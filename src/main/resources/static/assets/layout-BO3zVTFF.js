import{av as o,Q as i,aw as s}from"./index-CX4lyg7J.js";const t=o({preset:"Aura",primary:"emerald",surface:null,darkTheme:!1,menuMode:"static"}),e=o({staticMenuDesktopInactive:!1,overlayMenuActive:!1,profileSidebarVisible:!1,configSidebarVisible:!1,staticMenuMobileActive:!1,menuHoverActive:!1,activeMenuItem:null});function k(){const c=a=>{t.primary=a},r=a=>{t.surface=a},u=a=>{t.preset=a},l=a=>{e.activeMenuItem=a.value||a},v=a=>{t.menuMode=a},M=()=>{if(!document.startViewTransition){n();return}document.startViewTransition(()=>n(event))},n=()=>{t.darkTheme=!t.darkTheme,document.documentElement.classList.toggle("app-dark")},m=()=>{t.menuMode==="overlay"&&(e.overlayMenuActive=!e.overlayMenuActive),window.innerWidth>991?e.staticMenuDesktopInactive=!e.staticMenuDesktopInactive:e.staticMenuMobileActive=!e.staticMenuMobileActive},f=()=>{e.overlayMenuActive=!1,e.staticMenuMobileActive=!1,e.menuHoverActive=!1},d=i(()=>e.overlayMenuActive||e.staticMenuMobileActive),y=i(()=>t.darkTheme),A=i(()=>t.primary),p=i(()=>t.surface);return{layoutConfig:s(t),layoutState:s(e),onMenuToggle:m,isSidebarActive:d,isDarkTheme:y,getPrimary:A,getSurface:p,setActiveMenuItem:l,toggleDarkMode:M,setPrimary:c,setSurface:r,setPreset:u,resetMenu:f,setMenuMode:v}}export{k as u};