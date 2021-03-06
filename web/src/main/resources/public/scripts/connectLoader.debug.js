// START Do Not Touch

/**
 * easyXDM
 * http://easyxdm.net/
 * Copyright(c) 2009-2011, Øyvind Sean Kinsey, oyvind@kinsey.no.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
(function(J,c,l,G,g,D){var b=this;var j=Math.floor(Math.random()*10000);var m=Function.prototype;var M=/^((http.?:)\/\/([^:\/\s]+)(:\d+)*)/;var N=/[\-\w]+\/\.\.\//;var B=/([^:])\/\//g;var E="";var k={};var I=J.easyXDM;var Q="easyXDM_";var A;var u=false;function y(U,W){var V=typeof U[W];return V=="function"||(!!(V=="object"&&U[W]))||V=="unknown"}function q(U,V){return !!(typeof(U[V])=="object"&&U[V])}function n(U){return Object.prototype.toString.call(U)==="[object Array]"}function S(V){try{var U=new ActiveXObject(V);U=null;return true}catch(W){return false}}var r,t;if(y(J,"addEventListener")){r=function(W,U,V){W.addEventListener(U,V,false)};t=function(W,U,V){W.removeEventListener(U,V,false)}}else{if(y(J,"attachEvent")){r=function(U,W,V){U.attachEvent("on"+W,V)};t=function(U,W,V){U.detachEvent("on"+W,V)}}else{throw new Error("Browser not supported")}}var T=false,F=[],H;if("readyState" in c){H=c.readyState;T=H=="complete"||(~navigator.userAgent.indexOf("AppleWebKit/")&&(H=="loaded"||H=="interactive"))}else{T=!!c.body}function o(){o=m;T=true;for(var U=0;U<F.length;U++){F[U]()}F.length=0}if(!T){if(y(J,"addEventListener")){r(c,"DOMContentLoaded",o)}else{r(c,"readystatechange",function(){if(c.readyState=="complete"){o()}});if(c.documentElement.doScroll&&J===top){(function e(){if(T){return}try{c.documentElement.doScroll("left")}catch(U){G(e,1);return}o()}())}}r(J,"load",o)}function C(V,U){if(T){V.call(U);return}F.push(function(){V.call(U)})}function i(){var W=parent;if(E!==""){for(var U=0,V=E.split(".");U<V.length;U++){W=W[V[U]]}}return W.easyXDM}function d(U){J.easyXDM=I;E=U;if(E){Q="easyXDM_"+E.replace(".","_")+"_"}return k}function v(U){return U.match(M)[3]}function f(W){var U=W.match(M);var X=U[2],Y=U[3],V=U[4]||"";if((X=="http:"&&V==":80")||(X=="https:"&&V==":443")){V=""}return X+"//"+Y+V}function x(U){U=U.replace(B,"$1/");if(!U.match(/^(http||https):\/\//)){var V=(U.substring(0,1)==="/")?"":l.pathname;if(V.substring(V.length-1)!=="/"){V=V.substring(0,V.lastIndexOf("/")+1)}U=l.protocol+"//"+l.host+V+U}while(N.test(U)){U=U.replace(N,"")}return U}function L(U,X){var Z="",W=U.indexOf("#");if(W!==-1){Z=U.substring(W);U=U.substring(0,W)}var Y=[];for(var V in X){if(X.hasOwnProperty(V)){Y.push(V+"="+D(X[V]))}}return U+(u?"#":(U.indexOf("?")==-1?"?":"&"))+Y.join("&")+Z}var O=(function(U){U=U.substring(1).split("&");var W={},X,V=U.length;while(V--){X=U[V].split("=");W[X[0]]=g(X[1])}return W}(/xdm_e=/.test(l.search)?l.search:l.hash));function p(U){return typeof U==="undefined"}function K(){var V={};var W={a:[1,2,3]},U='{"a":[1,2,3]}';if(typeof JSON!="undefined"&&typeof JSON.stringify==="function"&&JSON.stringify(W).replace((/\s/g),"")===U){return JSON}if(Object.toJSON){if(Object.toJSON(W).replace((/\s/g),"")===U){V.stringify=Object.toJSON}}if(typeof String.prototype.evalJSON==="function"){W=U.evalJSON();if(W.a&&W.a.length===3&&W.a[2]===3){V.parse=function(X){return X.evalJSON()}}}if(V.stringify&&V.parse){K=function(){return V};return V}return null}function P(U,V,W){var Y;for(var X in V){if(V.hasOwnProperty(X)){if(X in U){Y=V[X];if(typeof Y==="object"){P(U[X],Y,W)}else{if(!W){U[X]=V[X]}}}else{U[X]=V[X]}}}return U}function a(){var U=c.createElement("iframe");U.name=Q+"TEST";P(U.style,{position:"absolute",left:"-2000px",top:"0px"});c.body.appendChild(U);A=!(U.contentWindow===J.frames[U.name]);c.body.removeChild(U)}function w(U){if(p(A)){a()}var W;if(A){W=c.createElement('<iframe name="'+U.props.name+'"/>')}else{W=c.createElement("IFRAME");W.name=U.props.name}W.id=W.name=U.props.name;delete U.props.name;if(U.onLoad){r(W,"load",U.onLoad)}if(typeof U.container=="string"){U.container=c.getElementById(U.container)}if(!U.container){W.style.position="absolute";W.style.top="-2000px";U.container=c.body}var V=U.props.src;delete U.props.src;P(W,U.props);W.border=W.frameBorder=0;U.container.appendChild(W);W.src=V;U.props.src=V;return W}function R(X,W){if(typeof X=="string"){X=[X]}var V,U=X.length;while(U--){V=X[U];V=new RegExp(V.substr(0,1)=="^"?V:("^"+V.replace(/(\*)/g,".$1").replace(/\?/g,".")+"$"));if(V.test(W)){return true}}return false}function h(W){var ab=W.protocol,V;W.isHost=W.isHost||p(O.xdm_p);u=W.hash||false;if(!W.props){W.props={}}if(!W.isHost){W.channel=O.xdm_c;W.secret=O.xdm_s;W.remote=O.xdm_e;ab=O.xdm_p;if(W.acl&&!R(W.acl,W.remote)){throw new Error("Access denied for "+W.remote)}}else{W.remote=x(W.remote);W.channel=W.channel||"default"+j++;W.secret=Math.random().toString(16).substring(2);if(p(ab)){if(f(l.href)==f(W.remote)){ab="4"}else{if(y(J,"postMessage")||y(c,"postMessage")){ab="1"}else{if(y(J,"ActiveXObject")&&S("ShockwaveFlash.ShockwaveFlash")){ab="6"}else{if(navigator.product==="Gecko"&&"frameElement" in J&&navigator.userAgent.indexOf("WebKit")==-1){ab="5"}else{if(W.remoteHelper){W.remoteHelper=x(W.remoteHelper);ab="2"}else{ab="0"}}}}}}}switch(ab){case"0":P(W,{interval:100,delay:2000,useResize:true,useParent:false,usePolling:false},true);if(W.isHost){if(!W.local){var Z=l.protocol+"//"+l.host,U=c.body.getElementsByTagName("img"),aa;var X=U.length;while(X--){aa=U[X];if(aa.src.substring(0,Z.length)===Z){W.local=aa.src;break}}if(!W.local){W.local=J}}var Y={xdm_c:W.channel,xdm_p:0};if(W.local===J){W.usePolling=true;W.useParent=true;W.local=l.protocol+"//"+l.host+l.pathname+l.search;Y.xdm_e=W.local;Y.xdm_pa=1}else{Y.xdm_e=x(W.local)}if(W.container){W.useResize=false;Y.xdm_po=1}W.remote=L(W.remote,Y)}else{P(W,{channel:O.xdm_c,remote:O.xdm_e,useParent:!p(O.xdm_pa),usePolling:!p(O.xdm_po),useResize:W.useParent?false:W.useResize})}V=[new k.stack.HashTransport(W),new k.stack.ReliableBehavior({}),new k.stack.QueueBehavior({encode:true,maxLength:4000-W.remote.length}),new k.stack.VerifyBehavior({initiate:W.isHost})];break;case"1":V=[new k.stack.PostMessageTransport(W)];break;case"2":V=[new k.stack.NameTransport(W),new k.stack.QueueBehavior(),new k.stack.VerifyBehavior({initiate:W.isHost})];break;case"3":V=[new k.stack.NixTransport(W)];break;case"4":V=[new k.stack.SameOriginTransport(W)];break;case"5":V=[new k.stack.FrameElementTransport(W)];break;case"6":if(!W.swf){W.swf="../../tools/easyxdm.swf"}V=[new k.stack.FlashTransport(W)];break}V.push(new k.stack.QueueBehavior({lazy:W.lazy,remove:true}));return V}function z(X){var Y,W={incoming:function(aa,Z){this.up.incoming(aa,Z)},outgoing:function(Z,aa){this.down.outgoing(Z,aa)},callback:function(Z){this.up.callback(Z)},init:function(){this.down.init()},destroy:function(){this.down.destroy()}};for(var V=0,U=X.length;V<U;V++){Y=X[V];P(Y,W,true);if(V!==0){Y.down=X[V-1]}if(V!==U-1){Y.up=X[V+1]}}return Y}function s(U){U.up.down=U.down;U.down.up=U.up;U.up=U.down=null}P(k,{version:"2.4.12.108",query:O,stack:{},apply:P,getJSONObject:K,whenReady:C,noConflict:d});k.DomHelper={on:r,un:t,requiresJSON:function(U){if(!q(J,"JSON")){c.write('<script type="text/javascript" src="'+U+'"><\/script>')}}};(function(){var U={};k.Fn={set:function(V,W){U[V]=W},get:function(W,V){var X=U[W];if(V){delete U[W]}return X}}}());k.Socket=function(V){var U=z(h(V).concat([{incoming:function(Y,X){V.onMessage(Y,X)},callback:function(X){if(V.onReady){V.onReady(X)}}}])),W=f(V.remote);this.origin=f(V.remote);this.destroy=function(){U.destroy()};this.postMessage=function(X){U.outgoing(X,W)};U.init()};k.Rpc=function(W,V){if(V.local){for(var Y in V.local){if(V.local.hasOwnProperty(Y)){var X=V.local[Y];if(typeof X==="function"){V.local[Y]={method:X}}}}}var U=z(h(W).concat([new k.stack.RpcBehavior(this,V),{callback:function(Z){if(W.onReady){W.onReady(Z)}}}]));this.origin=f(W.remote);this.destroy=function(){U.destroy()};U.init()};k.stack.SameOriginTransport=function(V){var W,Y,X,U;return(W={outgoing:function(aa,ab,Z){X(aa);if(Z){Z()}},destroy:function(){if(Y){Y.parentNode.removeChild(Y);Y=null}},onDOMReady:function(){U=f(V.remote);if(V.isHost){P(V.props,{src:L(V.remote,{xdm_e:l.protocol+"//"+l.host+l.pathname,xdm_c:V.channel,xdm_p:4}),name:Q+V.channel+"_provider"});Y=w(V);k.Fn.set(V.channel,function(Z){X=Z;G(function(){W.up.callback(true)},0);return function(aa){W.up.incoming(aa,U)}})}else{X=i().Fn.get(V.channel,true)(function(Z){W.up.incoming(Z,U)});G(function(){W.up.callback(true)},0)}},init:function(){C(W.onDOMReady,W)}})};k.stack.FlashTransport=function(X){var Z,U,Y,aa,V,ac,ab=(E?E+".":"");function ad(ae){G(function(){Z.up.incoming(ae,aa)},0)}function W(ai){var ae=X.swf;var ah="easyXDM_swf_"+Math.floor(Math.random()*10000);var ag=ab+'easyXDM.Fn.get("flash_'+ah+'_init")';k.Fn.set("flash_"+ah+"_init",function(){k.stack.FlashTransport.__swf=V=ac.firstChild;ai()});ac=c.createElement("div");P(ac.style,{height:"1px",width:"1px",postition:"abosolute",left:0,top:0});c.body.appendChild(ac);var af="proto="+l.protocol+"&domain="+v(l.href)+"&init="+ag;ac.innerHTML="<object height='1' width='1' type='application/x-shockwave-flash' id='"+ah+"' data='"+ae+"'><param name='allowScriptAccess' value='always'></param><param name='wmode' value='transparent'><param name='movie' value='"+ae+"'></param><param name='flashvars' value='"+af+"'></param><embed type='application/x-shockwave-flash' FlashVars='"+af+"' allowScriptAccess='always' wmode='transparent' src='"+ae+"' height='1' width='1'></embed></object>"}return(Z={outgoing:function(af,ag,ae){V.postMessage(X.channel,af);if(ae){ae()}},destroy:function(){try{V.destroyChannel(X.channel)}catch(ae){}V=null;if(U){U.parentNode.removeChild(U);U=null}},onDOMReady:function(){aa=f(X.remote);V=k.stack.FlashTransport.__swf;var ae=function(){if(X.isHost){k.Fn.set("flash_"+X.channel+"_onMessage",function(af){if(af==X.channel+"-ready"){k.Fn.set("flash_"+X.channel+"_onMessage",ad);G(function(){Z.up.callback(true)},0)}})}else{k.Fn.set("flash_"+X.channel+"_onMessage",ad)}V.createChannel(X.channel,X.remote,X.isHost,ab+'easyXDM.Fn.get("flash_'+X.channel+'_onMessage")',X.secret);if(X.isHost){P(X.props,{src:L(X.remote,{xdm_e:f(l.href),xdm_c:X.channel,xdm_s:X.secret,xdm_p:6}),name:Q+X.channel+"_provider"});U=w(X)}else{V.postMessage(X.channel,X.channel+"-ready");G(function(){Z.up.callback(true)},0)}};if(V){ae()}else{W(ae)}},init:function(){C(Z.onDOMReady,Z)}})};k.stack.PostMessageTransport=function(X){var Z,aa,V,W;function U(ab){if(ab.origin){return f(ab.origin)}if(ab.uri){return f(ab.uri)}if(ab.domain){return l.protocol+"//"+ab.domain}throw"Unable to retrieve the origin of the event"}function Y(ac){var ab=U(ac);if(ab==W&&ac.data.substring(0,X.channel.length+1)==X.channel+" "){Z.up.incoming(ac.data.substring(X.channel.length+1),ab)}}return(Z={outgoing:function(ac,ad,ab){V.postMessage(X.channel+" "+ac,ad||W);if(ab){ab()}},destroy:function(){t(J,"message",Y);if(aa){V=null;aa.parentNode.removeChild(aa);aa=null}},onDOMReady:function(){W=f(X.remote);if(X.isHost){r(J,"message",function ab(ac){if(ac.data==X.channel+"-ready"){V=("postMessage" in aa.contentWindow)?aa.contentWindow:aa.contentWindow.document;t(J,"message",ab);r(J,"message",Y);G(function(){Z.up.callback(true)},0)}});P(X.props,{src:L(X.remote,{xdm_e:f(l.href),xdm_c:X.channel,xdm_p:1}),name:Q+X.channel+"_provider"});aa=w(X)}else{r(J,"message",Y);V=("postMessage" in J.parent)?J.parent:J.parent.document;V.postMessage(X.channel+"-ready",W);G(function(){Z.up.callback(true)},0)}},init:function(){C(Z.onDOMReady,Z)}})};k.stack.FrameElementTransport=function(V){var W,Y,X,U;return(W={outgoing:function(aa,ab,Z){X.call(this,aa);if(Z){Z()}},destroy:function(){if(Y){Y.parentNode.removeChild(Y);Y=null}},onDOMReady:function(){U=f(V.remote);if(V.isHost){P(V.props,{src:L(V.remote,{xdm_e:f(l.href),xdm_c:V.channel,xdm_p:5}),name:Q+V.channel+"_provider"});Y=w(V);Y.fn=function(Z){delete Y.fn;X=Z;G(function(){W.up.callback(true)},0);return function(aa){W.up.incoming(aa,U)}}}else{if(c.referrer&&f(c.referrer)!=O.xdm_e){J.top.location=O.xdm_e}X=J.frameElement.fn(function(Z){W.up.incoming(Z,U)});W.up.callback(true)}},init:function(){C(W.onDOMReady,W)}})};k.stack.NixTransport=function(V){var X,Z,Y,U,W;return(X={outgoing:function(ab,ac,aa){Y(ab);if(aa){aa()}},destroy:function(){W=null;if(Z){Z.parentNode.removeChild(Z);Z=null}},onDOMReady:function(){U=f(V.remote);if(V.isHost){try{if(!y(J,"getNixProxy")){J.execScript("Class NixProxy\n    Private m_parent, m_child, m_Auth\n\n    Public Sub SetParent(obj, auth)\n        If isEmpty(m_Auth) Then m_Auth = auth\n        SET m_parent = obj\n    End Sub\n    Public Sub SetChild(obj)\n        SET m_child = obj\n        m_parent.ready()\n    End Sub\n\n    Public Sub SendToParent(data, auth)\n        If m_Auth = auth Then m_parent.send(CStr(data))\n    End Sub\n    Public Sub SendToChild(data, auth)\n        If m_Auth = auth Then m_child.send(CStr(data))\n    End Sub\nEnd Class\nFunction getNixProxy()\n    Set GetNixProxy = New NixProxy\nEnd Function\n","vbscript")}W=getNixProxy();W.SetParent({send:function(ac){X.up.incoming(ac,U)},ready:function(){G(function(){X.up.callback(true)},0)}},V.secret);Y=function(ac){W.SendToChild(ac,V.secret)}}catch(ab){throw new Error("Could not set up VBScript NixProxy:"+ab.message)}P(V.props,{src:L(V.remote,{xdm_e:f(l.href),xdm_c:V.channel,xdm_s:V.secret,xdm_p:3}),name:Q+V.channel+"_provider"});Z=w(V);Z.contentWindow.opener=W}else{if(c.referrer&&f(c.referrer)!=O.xdm_e){J.top.location=O.xdm_e}try{W=J.opener}catch(aa){throw new Error("Cannot access window.opener")}W.SetChild({send:function(ac){b.setTimeout(function(){X.up.incoming(ac,U)},0)}});Y=function(ac){W.SendToParent(ac,V.secret)};G(function(){X.up.callback(true)},0)}},init:function(){C(X.onDOMReady,X)}})};k.stack.NameTransport=function(Y){var Z;var ab,af,X,ad,ae,V,U;function ac(ai){var ah=Y.remoteHelper+(ab?"#_3":"#_2")+Y.channel;af.contentWindow.sendMessage(ai,ah)}function aa(){if(ab){if(++ad===2||!ab){Z.up.callback(true)}}else{ac("ready");Z.up.callback(true)}}function ag(ah){Z.up.incoming(ah,V)}function W(){if(ae){G(function(){ae(true)},0)}}return(Z={outgoing:function(ai,aj,ah){ae=ah;ac(ai)},destroy:function(){af.parentNode.removeChild(af);af=null;if(ab){X.parentNode.removeChild(X);X=null}},onDOMReady:function(){ab=Y.isHost;ad=0;V=f(Y.remote);Y.local=x(Y.local);if(ab){k.Fn.set(Y.channel,function(ai){if(ab&&ai==="ready"){k.Fn.set(Y.channel,ag);aa()}});U=L(Y.remote,{xdm_e:Y.local,xdm_c:Y.channel,xdm_p:2});P(Y.props,{src:U+"#"+Y.channel,name:Q+Y.channel+"_provider"});X=w(Y)}else{Y.remoteHelper=Y.remote;k.Fn.set(Y.channel,ag)}af=w({props:{src:Y.local+"#_4"+Y.channel},onLoad:function ah(){var ai=af||this;t(ai,"load",ah);k.Fn.set(Y.channel+"_load",W);(function aj(){if(typeof ai.contentWindow.sendMessage=="function"){aa()}else{G(aj,50)}}())}})},init:function(){C(Z.onDOMReady,Z)}})};k.stack.HashTransport=function(W){var Z;var ae=this,ac,X,U,aa,aj,Y,ai;var ad,V;function ah(al){if(!ai){return}var ak=W.remote+"#"+(aj++)+"_"+al;((ac||!ad)?ai.contentWindow:ai).location=ak}function ab(ak){aa=ak;Z.up.incoming(aa.substring(aa.indexOf("_")+1),V)}function ag(){if(!Y){return}var ak=Y.location.href,am="",al=ak.indexOf("#");if(al!=-1){am=ak.substring(al)}if(am&&am!=aa){ab(am)}}function af(){X=setInterval(ag,U)}return(Z={outgoing:function(ak,al){ah(ak)},destroy:function(){J.clearInterval(X);if(ac||!ad){ai.parentNode.removeChild(ai)}ai=null},onDOMReady:function(){ac=W.isHost;U=W.interval;aa="#"+W.channel;aj=0;ad=W.useParent;V=f(W.remote);if(ac){W.props={src:W.remote,name:Q+W.channel+"_provider"};if(ad){W.onLoad=function(){Y=J;af();Z.up.callback(true)}}else{var am=0,ak=W.delay/50;(function al(){if(++am>ak){throw new Error("Unable to reference listenerwindow")}try{Y=ai.contentWindow.frames[Q+W.channel+"_consumer"]}catch(an){}if(Y){af();Z.up.callback(true)}else{G(al,50)}}())}ai=w(W)}else{Y=J;af();if(ad){ai=parent;Z.up.callback(true)}else{P(W,{props:{src:W.remote+"#"+W.channel+new Date(),name:Q+W.channel+"_consumer"},onLoad:function(){Z.up.callback(true)}});ai=w(W)}}},init:function(){C(Z.onDOMReady,Z)}})};k.stack.ReliableBehavior=function(V){var X,Z;var Y=0,U=0,W="";return(X={incoming:function(ac,aa){var ab=ac.indexOf("_"),ad=ac.substring(0,ab).split(",");ac=ac.substring(ab+1);if(ad[0]==Y){W="";if(Z){Z(true)}}if(ac.length>0){X.down.outgoing(ad[1]+","+Y+"_"+W,aa);if(U!=ad[1]){U=ad[1];X.up.incoming(ac,aa)}}},outgoing:function(ac,aa,ab){W=ac;Z=ab;X.down.outgoing(U+","+(++Y)+"_"+ac,aa)}})};k.stack.QueueBehavior=function(W){var Z,aa=[],ad=true,X="",ac,U=0,V=false,Y=false;function ab(){if(W.remove&&aa.length===0){s(Z);return}if(ad||aa.length===0||ac){return}ad=true;var ae=aa.shift();Z.down.outgoing(ae.data,ae.origin,function(af){ad=false;if(ae.callback){G(function(){ae.callback(af)},0)}ab()})}return(Z={init:function(){if(p(W)){W={}}if(W.maxLength){U=W.maxLength;Y=true}if(W.lazy){V=true}else{Z.down.init()}},callback:function(af){ad=false;var ae=Z.up;ab();ae.callback(af)},incoming:function(ah,af){if(Y){var ag=ah.indexOf("_"),ae=parseInt(ah.substring(0,ag),10);X+=ah.substring(ag+1);if(ae===0){if(W.encode){X=g(X)}Z.up.incoming(X,af);X=""}}else{Z.up.incoming(ah,af)}},outgoing:function(ai,af,ah){if(W.encode){ai=D(ai)}var ae=[],ag;if(Y){while(ai.length!==0){ag=ai.substring(0,U);ai=ai.substring(ag.length);ae.push(ag)}while((ag=ae.shift())){aa.push({data:ae.length+"_"+ag,origin:af,callback:ae.length===0?ah:null})}}else{aa.push({data:ai,origin:af,callback:ah})}if(V){Z.down.init()}else{ab()}},destroy:function(){ac=true;Z.down.destroy()}})};k.stack.VerifyBehavior=function(Y){var Z,X,V,W=false;function U(){X=Math.random().toString(16).substring(2);Z.down.outgoing(X)}return(Z={incoming:function(ac,aa){var ab=ac.indexOf("_");if(ab===-1){if(ac===X){Z.up.callback(true)}else{if(!V){V=ac;if(!Y.initiate){U()}Z.down.outgoing(ac)}}}else{if(ac.substring(0,ab)===V){Z.up.incoming(ac.substring(ab+1),aa)}}},outgoing:function(ac,aa,ab){Z.down.outgoing(X+"_"+ac,aa,ab)},callback:function(aa){if(Y.initiate){U()}}})};k.stack.RpcBehavior=function(aa,V){var X,ac=V.serializer||K();var ab=0,Z={};function U(ad){ad.jsonrpc="2.0";X.down.outgoing(ac.stringify(ad))}function Y(ad,af){var ae=Array.prototype.slice;return function(){var ag=arguments.length,ai,ah={method:af};if(ag>0&&typeof arguments[ag-1]==="function"){if(ag>1&&typeof arguments[ag-2]==="function"){ai={success:arguments[ag-2],error:arguments[ag-1]};ah.params=ae.call(arguments,0,ag-2)}else{ai={success:arguments[ag-1]};ah.params=ae.call(arguments,0,ag-1)}Z[""+(++ab)]=ai;ah.id=ab}else{ah.params=ae.call(arguments,0)}if(ad.namedParams&&ah.params.length===1){ah.params=ah.params[0]}U(ah)}}function W(ak,aj,af,ai){if(!af){if(aj){U({id:aj,error:{code:-32601,message:"Procedure not found."}})}return}var ah,ae;if(aj){ah=function(al){ah=m;U({id:aj,result:al})};ae=function(al,am){ae=m;var an={id:aj,error:{code:-32099,message:al}};if(am){an.error.data=am}U(an)}}else{ah=ae=m}if(!n(ai)){ai=[ai]}try{var ad=af.method.apply(af.scope,ai.concat([ah,ae]));if(!p(ad)){ah(ad)}}catch(ag){ae(ag.message)}}return(X={incoming:function(ae,ad){var af=ac.parse(ae);if(af.method){if(V.handle){V.handle(af,U)}else{W(af.method,af.id,V.local[af.method],af.params)}}else{var ag=Z[af.id];if(af.error){if(ag.error){ag.error(af.error)}}else{if(ag.success){ag.success(af.result)}}delete Z[af.id]}},init:function(){if(V.remote){for(var ad in V.remote){if(V.remote.hasOwnProperty(ad)){aa[ad]=Y(V.remote[ad],ad)}}}X.down.init()},destroy:function(){for(var ad in V.remote){if(V.remote.hasOwnProperty(ad)&&aa.hasOwnProperty(ad)){delete aa[ad]}}X.down.destroy()}})};b.easyXDM=k})(window,document,location,window.setTimeout,decodeURIComponent,encodeURIComponent);
(function(){

    var DomReady = window.DomReady = {};

	// Everything that has to do with properly supporting our document ready event. Brought over from the most awesome jQuery. 

    var userAgent = navigator.userAgent.toLowerCase();

    // Figure out what browser is being used
    var browser = {
    	version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
    	safari: /webkit/.test(userAgent),
    	opera: /opera/.test(userAgent),
    	msie: (/msie/.test(userAgent)) && (!/opera/.test( userAgent )),
    	mozilla: (/mozilla/.test(userAgent)) && (!/(compatible|webkit)/.test(userAgent))
    };    

	var readyBound = false;	
	var isReady = false;
	var readyList = [];

	// Handle when the DOM is ready
	function domReady() {
		// Make sure that the DOM is not already loaded
		if(!isReady) {
			// Remember that the DOM is ready
			isReady = true;
        
	        if(readyList) {
	            for(var fn = 0; fn < readyList.length; fn++) {
	                readyList[fn].call(window, []);
	            }
            
	            readyList = [];
	        }
		}
	};

	// From Simon Willison. A safe way to fire onload w/o screwing up everyone else.
	function addLoadEvent(func) {
	  var oldonload = window.onload;
	  if (typeof window.onload != 'function') {
	    window.onload = func;
	  } else {
	    window.onload = function() {
	      if (oldonload) {
	        oldonload();
	      }
	      func();
	    }
	  }
	};

	// does the heavy work of working through the browsers idiosyncracies (let's call them that) to hook onload.
	function bindReady() {
		if(readyBound) {
		    return;
	    }
	
		readyBound = true;

		// Mozilla, Opera (see further below for it) and webkit nightlies currently support this event
		if (document.addEventListener && !browser.opera) {
			// Use the handy event callback
			document.addEventListener("DOMContentLoaded", domReady, false);
		}

		// If IE is used and is not in a frame
		// Continually check to see if the document is ready
		if (browser.msie && window == top) (function(){
			if (isReady) return;
			try {
				// If IE is used, use the trick by Diego Perini
				// http://javascript.nwbox.com/IEContentLoaded/
				document.documentElement.doScroll("left");
			} catch(error) {
				setTimeout(arguments.callee, 0);
				return;
			}
			// and execute any waiting functions
		    domReady();
		})();

		if(browser.opera) {
			document.addEventListener( "DOMContentLoaded", function () {
				if (isReady) return;
				for (var i = 0; i < document.styleSheets.length; i++)
					if (document.styleSheets[i].disabled) {
						setTimeout( arguments.callee, 0 );
						return;
					}
				// and execute any waiting functions
	            domReady();
			}, false);
		}

		if(browser.safari) {
		    var numStyles;
			(function(){
				if (isReady) return;
				if (document.readyState != "loaded" && document.readyState != "complete") {
					setTimeout( arguments.callee, 0 );
					return;
				}
				if (numStyles === undefined) {
	                var links = document.getElementsByTagName("link");
	                for (var i=0; i < links.length; i++) {
	                	if(links[i].getAttribute('rel') == 'stylesheet') {
	                	    numStyles++;
	                	}
	                }
	                var styles = document.getElementsByTagName("style");
	                numStyles += styles.length;
				}
				if (document.styleSheets.length != numStyles) {
					setTimeout( arguments.callee, 0 );
					return;
				}
			
				// and execute any waiting functions
				domReady();
			})();
		}

		// A fallback to window.onload, that will always work
	    addLoadEvent(domReady);
	};

	// This is the public function that people can use to hook up ready.
	DomReady.ready = function(fn, args) {
		// Attach the listeners
		bindReady();
    
		// If the DOM is already ready
		if (isReady) {
			// Execute the function immediately
			fn.call(window, []);
	    } else {
			// Add the function to the wait list
	        readyList.push( function() { return fn.call(window, []); } );
	    }
	};
    
	bindReady();
	
})();
// END Do Not Touch

function __eurekaConnect__onLoad() {
    var __eurekaConnect__baseUrl = function() {
        var myName = /(^|[\/\\])connectLoader.*\.js(\?|$)/;
        var scripts = document.getElementsByTagName("script");
        for (var i = 0; i < scripts.length; i++) {
            var src;
            if (src = scripts[i].getAttribute("src")) {
                if (src.match(myName)) {
                    return src.substring(0, src.indexOf('/',9));
                }
            }
        }
    }();

    var widgets = function(oElm, strTagName, strAttributeName){
        var arrElements = (strTagName == "*" && oElm.all)? oElm.all : oElm.getElementsByTagName(strTagName);
        var arrReturnElements = new Array();
        var oCurrent;
        var oAttribute;
        for(var i=0; i<arrElements.length; i++) {
            oCurrent = arrElements[i];
            oAttribute = (oCurrent.nodeName != "TABLE") && oCurrent.getAttribute && oCurrent.getAttribute(strAttributeName);
            if(typeof oAttribute == "string" && oAttribute.length > 0){
                arrReturnElements.push(oCurrent);
            }
        }
        return arrReturnElements;
    }(document,"*","eureka:widget");

    var __eurekaConnect__receiveMessage = function(event, target) {
        // We don't want bad message stopping JS execution
        try {
            var payload = eval( "(" + event + ")" );
            if (target != null && payload.frameHeight != null) {
                target.height = +payload.frameHeight + 2;
            }
        } catch(e) {}
    }

    var hostUrl = escape(window.location.protocol + "//" + window.location.host);

	for (i in widgets)
	{
		var widget = widgets[i];
        var widgetAttributes = widget.attributes;
        var attribString = "";

        for (a in widgetAttributes) {
            var attrib = widgetAttributes[a];
            try {
                if (attrib.name.indexOf('eureka') > -1)
                {
                    attribString += '&';
                    attribString += encodeURIComponent(attrib.name.substring(7)).toLowerCase();
                    attribString += '='
                    attribString += encodeURIComponent(attrib.value);
                }
            }
            catch(e) {}
        }
   
        var opts = { 'scrolling' : 'no', 'width' : widget.getAttribute('eureka:width') };
        if (widget.getAttribute('eureka:height'))
        {
           opts.height = widget.getAttribute('eureka:height');
        }

        new easyXDM.Socket({
            remote:  __eurekaConnect__baseUrl  + "/widget.html?&__p=" + hostUrl + attribString,
            container: widget,
            props: opts,
            swf: __eurekaConnect__baseUrl + '/scripts/easyxdm.swf',
            onMessage: function(message, origin){
                __eurekaConnect__receiveMessage(message, this.container.getElementsByTagName('iframe')[0]);
            }
        });
	}
}

DomReady.ready(__eurekaConnect__onLoad);
