const COMPARE_KEY='celucheck_compare_v1';

document.addEventListener('DOMContentLoaded',()=>{
  setupMenu();
  setupProductCards();
  setupFavorites();
  setupComparison();
  setupCarousel('#new-carousel','#new-prev','#new-next');
  setupChat();
});

function setupMenu(){
  const button=document.querySelector('#mobile-menu-toggle'),menu=document.querySelector('#mobile-menu');
  button?.addEventListener('click',()=>{
    const open=menu.classList.toggle('hidden')===false;
    button.setAttribute('aria-expanded',String(open));
    button.setAttribute('aria-label',open?'Cerrar menú':'Abrir menú');
  });
  document.addEventListener('keydown',event=>{
    if(event.key!=='Escape')return;
    menu?.classList.add('hidden');
    button?.setAttribute('aria-expanded','false');
  });
}

function setupProductCards(){
  document.querySelectorAll('.js-product-card').forEach(card=>{
    card.addEventListener('click',event=>{if(!event.target.closest('a,button'))location.href=card.dataset.href});
    card.addEventListener('keydown',event=>{
      if((event.key==='Enter'||event.key===' ')&&!event.target.closest('a,button')){
        event.preventDefault();location.href=card.dataset.href;
      }
    });
  });
}

function setupFavorites(){
  const token=document.querySelector('meta[name="_csrf"]')?.content||'';
  const header=document.querySelector('meta[name="_csrf_header"]')?.content||'X-CSRF-TOKEN';
  document.querySelectorAll('.favorite-btn').forEach(button=>button.addEventListener('click',async event=>{
    event.stopPropagation();
    if(button.dataset.loading==='true')return;
    button.dataset.loading='true';button.setAttribute('aria-busy','true');
    try{
      const response=await fetch(`/api/favorites/${button.dataset.id}`,{method:'POST',headers:{[header]:token}});
      if(response.status===401||response.redirected){location.href='/login';return}
      if(!response.ok)throw new Error();
      const data=await response.json();
      button.textContent=data.saved?'♥':'♡';button.classList.toggle('text-red-500',data.saved);
      button.setAttribute('aria-label',data.saved?'Quitar de favoritos':'Guardar en favoritos');
      updateFavoriteCount(data.saved?1:-1);showToast(data.saved?'Guardado en tus favoritos':'Eliminado de favoritos','success');
    }catch{showToast('Inicia sesión para guardar favoritos','error');setTimeout(()=>location.href='/login',700)}
    finally{delete button.dataset.loading;button.removeAttribute('aria-busy')}
  }));
}

function readComparison(){
  try{return JSON.parse(localStorage.getItem(COMPARE_KEY)||'[]').filter(item=>item&&item.id).slice(0,3)}catch{return[]}
}
function saveComparison(items){localStorage.setItem(COMPARE_KEY,JSON.stringify(items))}
function comparisonUrl(items){return '/comparar?ids='+items.map(item=>encodeURIComponent(item.id)).join(',')}

function setupComparison(){
  let selected=readComparison();
  const tray=document.querySelector('#compare-tray');
  const itemsBox=document.querySelector('#compare-items');
  const trayCount=document.querySelector('#compare-count');
  const goButton=document.querySelector('#compare-go');
  const pageProducts=[...document.querySelectorAll('.compare-page-remove')].map(button=>({id:String(button.dataset.id),name:button.dataset.name,image:button.dataset.image}));
  const initialParams=new URLSearchParams(location.search);
  if(location.pathname==='/comparar'&&initialParams.has('ids')){selected=pageProducts;saveComparison(selected)}

  const render=()=>{
    const total=selected.length;
    document.querySelectorAll('#nav-compare-count,#mobile-compare-count').forEach(element=>{
      element.textContent=String(total);element.classList.toggle('compare-count-pop',total>0);
    });
    document.querySelectorAll('.compare-btn').forEach(button=>{
      const active=selected.some(item=>String(item.id)===String(button.dataset.id));
      const compact=Boolean(button.closest('.product-card'));
      button.classList.toggle('compare-active',active);
      button.textContent=compact?(active?'✓':'＋'):(active?'✓ En el comparador':'＋ Añadir al comparador');
      button.setAttribute('aria-label',active?'Quitar del comparador':'Añadir al comparador');
      button.setAttribute('aria-pressed',String(active));
    });
    if(!tray)return;
    tray.classList.toggle('hidden',total===0);itemsBox.innerHTML='';
    selected.forEach(item=>{
      const chip=document.createElement('div');chip.className='compare-chip flex shrink-0 items-center gap-2 rounded-xl bg-white/10 p-2 pr-3';
      chip.innerHTML=`<img src="${escapeHtml(item.image)}" alt="" referrerpolicy="no-referrer" class="h-9 w-9 rounded-lg bg-white object-contain p-1"><span class="max-w-28 truncate text-xs font-bold">${escapeHtml(item.name)}</span><button type="button" class="compare-remove text-slate-400 hover:text-white" data-id="${escapeHtml(String(item.id))}" aria-label="Quitar ${escapeHtml(item.name)}">×</button>`;
      itemsBox.append(chip);
    });
    trayCount.textContent=`${total}/3`;
    goButton.disabled=total<2;
    goButton.textContent=total<2?'Añade 1 más':'Comparar ahora';
  };

  document.querySelectorAll('.compare-btn').forEach(button=>button.addEventListener('click',event=>{
    event.stopPropagation();
    const id=String(button.dataset.id),index=selected.findIndex(item=>String(item.id)===id);
    if(index>=0){
      const removed=selected[index];selected.splice(index,1);saveComparison(selected);render();
      showToast(`${removed.name} salió de la comparación`);
      return;
    }
    if(selected.length>=3){showToast('Ya tienes 3 equipos. Quita uno para añadir otro.','error');return}
    selected.push({id,name:button.dataset.name,image:button.dataset.image});
    saveComparison(selected);render();
    showToast(`${button.dataset.name} añadido · ${selected.length} de 3`,'success');
  }));

  itemsBox?.addEventListener('click',event=>{
    const button=event.target.closest('.compare-remove');if(!button)return;
    selected=selected.filter(item=>String(item.id)!==button.dataset.id);saveComparison(selected);render();
  });
  document.querySelector('#compare-clear')?.addEventListener('click',()=>{selected=[];saveComparison(selected);render();showToast('Comparador limpio')});
  goButton?.addEventListener('click',()=>{if(selected.length>=2)location.href=comparisonUrl(selected)});
  document.querySelectorAll('.compare-page-remove').forEach(button=>button.addEventListener('click',()=>{
    selected=selected.filter(item=>String(item.id)!==String(button.dataset.id));saveComparison(selected);
    location.href=selected.length?comparisonUrl(selected):'/comparar';
  }));
  document.querySelector('#compare-page-clear')?.addEventListener('click',()=>{selected=[];saveComparison(selected);location.href='/comparar'});
  document.querySelector('#compare-share')?.addEventListener('click',async()=>{
    try{await navigator.clipboard.writeText(location.href);showToast('Enlace de comparación copiado','success')}
    catch{showToast('No se pudo copiar el enlace','error')}
  });

  document.querySelectorAll('.js-compare-link').forEach(link=>link.addEventListener('click',event=>{
    selected=readComparison();
    if(selected.length>=2){event.preventDefault();location.href=comparisonUrl(selected);return}
    if(location.pathname==='/'||location.pathname==='/catalogo'){
      event.preventDefault();document.querySelector('#catalogo')?.scrollIntoView({behavior:'smooth',block:'start'});
      showToast(selected.length===1?'Añade un celular más para comparar':'Elige al menos 2 celulares para comparar','error');
    }
  }));

  const params=new URLSearchParams(location.search);
  if(location.pathname==='/comparar'&&!params.get('ids')&&selected.length>=2){location.replace(comparisonUrl(selected));return}
  window.addEventListener('storage',()=>{selected=readComparison();render()});
  render();
}

function setupCarousel(trackSelector,previousSelector,nextSelector){
  const track=document.querySelector(trackSelector);if(!track)return;
  const move=direction=>track.scrollBy({left:direction*Math.min(track.clientWidth*.82,620),behavior:'smooth'});
  document.querySelector(previousSelector)?.addEventListener('click',()=>move(-1));
  document.querySelector(nextSelector)?.addEventListener('click',()=>move(1));
}

function setupChat(){
  const toggle=document.querySelector('#chat-toggle'),panel=document.querySelector('#chat-panel'),close=document.querySelector('#chat-close');
  const setOpen=open=>{panel?.classList.toggle('hidden',!open);toggle?.setAttribute('aria-expanded',String(open));if(open)setTimeout(()=>document.querySelector('#chat-input')?.focus(),80)};
  toggle?.addEventListener('click',()=>setOpen(panel.classList.contains('hidden')));close?.addEventListener('click',()=>setOpen(false));
  document.querySelectorAll('#open-chat-cta').forEach(button=>button.addEventListener('click',()=>setOpen(true)));
  document.addEventListener('keydown',event=>{if(event.key==='Escape')setOpen(false)});
  document.querySelector('#chat-form')?.addEventListener('submit',async event=>{
    event.preventDefault();const input=document.querySelector('#chat-input'),box=document.querySelector('#chat-messages'),submit=event.currentTarget.querySelector('button'),value=input.value.trim();if(!value)return;
    appendMessage(box,value,'user');input.value='';input.disabled=true;submit.disabled=true;const wait=appendMessage(box,'Analizando tu consulta…','loading');
    try{const response=await fetch('/api/chat',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({message:value})});if(!response.ok)throw new Error();const data=await response.json();wait.textContent=data.answer||'No pude responder ahora.'}
    catch{wait.textContent='Hubo un problema al consultar. Inténtalo nuevamente.'}
    finally{input.disabled=false;submit.disabled=false;input.focus();box.scrollTop=box.scrollHeight}
  });
}

function escapeHtml(value){return value.replace(/[&<>'"]/g,char=>({'&':'&amp;','<':'&lt;','>':'&gt;',"'":'&#39;','"':'&quot;'}[char]))}
function appendMessage(box,text,type){const element=document.createElement('div');element.className=type==='user'?'message-in ml-auto max-w-[88%] rounded-2xl rounded-tr-sm bg-brand p-3 text-sm leading-6 text-white':'message-in max-w-[88%] rounded-2xl rounded-tl-sm bg-white p-3 text-sm leading-6 shadow-sm';element.textContent=text;box.append(element);box.scrollTop=box.scrollHeight;return element}
function updateFavoriteCount(delta){document.querySelectorAll('a[href="/favoritos"] span').forEach(element=>{const count=Number(element.textContent||0);element.textContent=Math.max(0,count+delta)})}
function showToast(message,type=''){const region=document.querySelector('#toast-region');if(!region)return;const element=document.createElement('div');element.className=`toast ${type}`;element.textContent=message;region.append(element);setTimeout(()=>{element.style.opacity='0';element.style.transform='translateY(-6px)';setTimeout(()=>element.remove(),200)},2800)}
