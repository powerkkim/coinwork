import{s as c}from"./index-BVqmq_S0.js";import{B as u,o as r,c as o,x as l,y as n,N as h,f as d,b as f}from"./index-CX4lyg7J.js";var m={root:"p-fluid"},b=u.extend({name:"fluid",classes:m}),v={name:"BaseFluid",extends:c,style:b,provide:function(){return{$pcFluid:this,$parentInstance:this}}},$={name:"Fluid",extends:v,inheritAttrs:!1};function x(e,a,t,i,p,s){return r(),o("div",n({class:e.cx("root")},e.ptmi("root")),[l(e.$slots,"default")],16)}$.render=x;var g=function(a){var t=a.dt;return`
.p-textarea {
    font-family: inherit;
    font-feature-settings: inherit;
    font-size: 1rem;
    color: `.concat(t("textarea.color"),`;
    background: `).concat(t("textarea.background"),`;
    padding: `).concat(t("textarea.padding.y")," ").concat(t("textarea.padding.x"),`;
    border: 1px solid `).concat(t("textarea.border.color"),`;
    transition: background `).concat(t("textarea.transition.duration"),", color ").concat(t("textarea.transition.duration"),", border-color ").concat(t("textarea.transition.duration"),", outline-color ").concat(t("textarea.transition.duration"),", box-shadow ").concat(t("textarea.transition.duration"),`;
    appearance: none;
    border-radius: `).concat(t("textarea.border.radius"),`;
    outline-color: transparent;
    box-shadow: `).concat(t("textarea.shadow"),`;
}

.p-textarea:enabled:hover {
    border-color: `).concat(t("textarea.hover.border.color"),`;
}

.p-textarea:enabled:focus {
    border-color: `).concat(t("textarea.focus.border.color"),`;
    box-shadow: `).concat(t("textarea.focus.ring.shadow"),`;
    outline: `).concat(t("textarea.focus.ring.width")," ").concat(t("textarea.focus.ring.style")," ").concat(t("textarea.focus.ring.color"),`;
    outline-offset: `).concat(t("textarea.focus.ring.offset"),`;
}

.p-textarea.p-invalid {
    border-color: `).concat(t("textarea.invalid.border.color"),`;
}

.p-textarea.p-variant-filled {
    background: `).concat(t("textarea.filled.background"),`;
}

.p-textarea.p-variant-filled:enabled:focus {
    background: `).concat(t("textarea.filled.focus.background"),`;
}

.p-textarea:disabled {
    opacity: 1;
    background: `).concat(t("textarea.disabled.background"),`;
    color: `).concat(t("textarea.disabled.color"),`;
}

.p-textarea::placeholder {
    color: `).concat(t("textarea.placeholder.color"),`;
}

.p-textarea-fluid {
    width: 100%;
}

.p-textarea-resizable {
    overflow: hidden;
    resize: none;
}
`)},y={root:function(a){var t=a.instance,i=a.props;return["p-textarea p-component",{"p-filled":t.filled,"p-textarea-resizable ":i.autoResize,"p-invalid":i.invalid,"p-variant-filled":i.variant?i.variant==="filled":t.$primevue.config.inputStyle==="filled"||t.$primevue.config.inputVariant==="filled","p-textarea-fluid":t.hasFluid}]}},k=u.extend({name:"textarea",theme:g,classes:y}),z={name:"BaseTextarea",extends:c,props:{modelValue:null,autoResize:Boolean,invalid:{type:Boolean,default:!1},variant:{type:String,default:null},fluid:{type:Boolean,default:null}},style:k,provide:function(){return{$pcTextarea:this,$parentInstance:this}}},w={name:"Textarea",extends:z,inheritAttrs:!1,emits:["update:modelValue"],inject:{$pcFluid:{default:null}},mounted:function(){this.$el.offsetParent&&this.autoResize&&this.resize()},updated:function(){this.$el.offsetParent&&this.autoResize&&this.resize()},methods:{resize:function(){this.$el.style.height="auto",this.$el.style.height=this.$el.scrollHeight+"px",parseFloat(this.$el.style.height)>=parseFloat(this.$el.style.maxHeight)?(this.$el.style.overflowY="scroll",this.$el.style.height=this.$el.style.maxHeight):this.$el.style.overflow="hidden"},onInput:function(a){this.autoResize&&this.resize(),this.$emit("update:modelValue",a.target.value)}},computed:{filled:function(){return this.modelValue!=null&&this.modelValue.toString().length>0},ptmParams:function(){return{context:{disabled:this.$attrs.disabled||this.$attrs.disabled===""}}},hasFluid:function(){return h(this.fluid)?!!this.$pcFluid:this.fluid}}},B=["value","aria-invalid"];function F(e,a,t,i,p,s){return r(),o("textarea",n({class:e.cx("root"),value:e.modelValue,"aria-invalid":e.invalid||void 0,onInput:a[0]||(a[0]=function(){return s.onInput&&s.onInput.apply(s,arguments)})},e.ptmi("root",s.ptmParams)),null,16,B)}w.render=F;var V=function(a){var t=a.dt;return`
.p-card {
    background: `.concat(t("card.background"),`;
    color: `).concat(t("card.color"),`;
    box-shadow: `).concat(t("card.shadow"),`;
    border-radius: `).concat(t("card.border.radius"),`;
    display: flex;
    flex-direction: column;
}

.p-card-caption {
    display: flex;
    flex-direction: column;
    gap: `).concat(t("card.caption.gap"),`;
}

.p-card-body {
    padding: `).concat(t("card.body.padding"),`;
    display: flex;
    flex-direction: column;
    gap: `).concat(t("card.body.gap"),`;
}

.p-card-title {
    font-size: `).concat(t("card.title.font.size"),`;
    font-weight: `).concat(t("card.title.font.weight"),`;
}

.p-card-subtitle {
    color: `).concat(t("card.subtitle.color"),`;
}
`)},I={root:"p-card p-component",header:"p-card-header",body:"p-card-body",caption:"p-card-caption",title:"p-card-title",subtitle:"p-card-subtitle",content:"p-card-content",footer:"p-card-footer"},S=u.extend({name:"card",theme:V,classes:I}),P={name:"BaseCard",extends:c,style:S,provide:function(){return{$pcCard:this,$parentInstance:this}}},C={name:"Card",extends:P,inheritAttrs:!1};function R(e,a,t,i,p,s){return r(),o("div",n({class:e.cx("root")},e.ptmi("root")),[e.$slots.header?(r(),o("div",n({key:0,class:e.cx("header")},e.ptm("header")),[l(e.$slots,"header")],16)):d("",!0),f("div",n({class:e.cx("body")},e.ptm("body")),[e.$slots.title||e.$slots.subtitle?(r(),o("div",n({key:0,class:e.cx("caption")},e.ptm("caption")),[e.$slots.title?(r(),o("div",n({key:0,class:e.cx("title")},e.ptm("title")),[l(e.$slots,"title")],16)):d("",!0),e.$slots.subtitle?(r(),o("div",n({key:1,class:e.cx("subtitle")},e.ptm("subtitle")),[l(e.$slots,"subtitle")],16)):d("",!0)],16)):d("",!0),f("div",n({class:e.cx("content")},e.ptm("content")),[l(e.$slots,"content")],16),e.$slots.footer?(r(),o("div",n({key:1,class:e.cx("footer")},e.ptm("footer")),[l(e.$slots,"footer")],16)):d("",!0)],16)],16)}C.render=R;export{$ as a,w as b,C as s};
