/*
 *dialog
 *
*/

(function($) {
    $.fn.dialog = function(opt) {
        opt = opt || {};
        $this_div = this;
        var div_dialog = this;
        // if(this.selector=="")
        //{
        var ov = $("div.for_all#overlay");
        if (ov.length == 0) {
            /*$("body").append(
            '<div id="overlay" class="for_all apple_overlay"><div class="close"></div><div class="corner_tl"><div class="corner_tr"><div class="corner_bl"><div class="corner_br"><div class="contentWrap"></div></div></div></div></div></div>'						
            );*/
            $("body").append("<div id='overlay' class='for_all apple_overlay'><table align='center' cellpadding='0' cellspacing='0'>"
					+ "<tr>"
						+ "<td class='corner_tl'></td>"
						+ "<td class='corner_tm'></td>"
						+ "<td class='corner_tr'></td>"
					  + "</tr>"
					  + "<tr>"
						+ "<td class='corner_ml'></td>"
						+ "<td class='corner_mm'><div class='contentWrap'></div></td>"//内容
						+ "<td class='corner_mr'></td>"
					  + "</tr>"
					  + "<tr>"
						+ "<td class='corner_bl'></td>"
						+ "<td class='corner_bm'></td>"
						+ "<td class='corner_br'></td>"
					  + "</tr></table></div>"
				 );
            ov = $("body").find("div.for_all#overlay");
            ov.bgiframe();
        }
        if (opt.title) {
            $(this).addClass("div_box").prepend("<h1>" + opt.title + "</h1>");
        }
        ov.find(".contentWrap").html("");
        ov.find(".contentWrap").append(this);
        div_dialog = ov;
        // }
        var target = $("div#overlay_target");

        if (target.length == 0) {
            $("body").append("<div id=overlay_target style='display: none;'> </div>");
            target = $("div#overlay_target");
        }

        if (opt.buttons) {
            var btns = this.find("div.btns_class");
            if (btns.length == 0) {
                $(this).append("<div class='btns_class'></div>");
                btns = $("div.btns_class", $(this));
            }
            var f = function(j) { return opt.buttons[j] }
            for (var i in opt.buttons) {
                if (btns.find("button#id_" + i).length == 0)
                    btns.append("<button type='button' class='btn' id='id_" + i + "'>" + gettext(i) + "</button>")
                else
                    btns.find("button#id_" + i).text(gettext(i));
                var b = btns.find("button#id_" + i)
                b.click(f(i));
            }
        }
        //alert(this.parent().find("div#id_close").length);
        if (this.parent().find("div#id_close").length == 0) {
            this.prepend("<div id='id_close' class='close'></div>");
        } this.find("div#id_close").click(function() {
            target.overlay().close();
            $this_div.remove();
        });

        var opt_overlay = {
            expose: {
                color: '#000000',
                loadSpeed: 200,
                opacity: 0.6
            },
            opacity: '0.6',
            effect: 'apple',
            close: "#id_close",
            fixed: false,
            closeOnClick: false,
            target: div_dialog,
            closeOnEsc: false
        }
        if (opt.on_load) {
            opt_overlay["onLoad"] = opt.on_load;
        }

        ret = target.overlay(opt_overlay);
        target.click();
        $("body div:last").bgiframe();
        return ret;
    };
})(jQuery);
//jquery.bgiframe.js

/* Copyright (c) 2006 Brandon Aaron (http://brandonaaron.net)
* Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php) 
* and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
*
* $LastChangedDate: 2007-07-21 18:44:59 -0500 (Sat, 21 Jul 2007) $
* $Rev: 2446 $
*
* Version 2.1.1
*/

(function($) {

    /**
    * The bgiframe is chainable and applies the iframe hack to get 
    * around zIndex issues in IE6. It will only apply itself in IE6 
    * and adds a class to the iframe called 'bgiframe'. The iframe
    * is appeneded as the first child of the matched element(s) 
    * with a tabIndex and zIndex of -1.
    * 
    * By default the plugin will take borders, sized with pixel units,
    * into account. If a different unit is used for the border's width,
    * then you will need to use the top and left settings as explained below.
    *
    * NOTICE: This plugin has been reported to cause perfromance problems
    * when used on elements that change properties (like width, height and
    * opacity) a lot in IE6. Most of these problems have been caused by 
    * the expressions used to calculate the elements width, height and 
    * borders. Some have reported it is due to the opacity filter. All 
    * these settings can be changed if needed as explained below.
    *
    * @example $('div').bgiframe();
    * @before <div><p>Paragraph</p></div>
    * @result <div><iframe class="bgiframe".../><p>Paragraph</p></div>
    *
    * @param Map settings Optional settings to configure the iframe.
    * @option String|Number top The iframe must be offset to the top
    * 		by the width of the top border. This should be a negative 
    *      number representing the border-top-width. If a number is 
    * 		is used here, pixels will be assumed. Otherwise, be sure
    *		to specify a unit. An expression could also be used. 
    * 		By default the value is "auto" which will use an expression 
    * 		to get the border-top-width if it is in pixels.
    * @option String|Number left The iframe must be offset to the left
    * 		by the width of the left border. This should be a negative 
    *      number representing the border-left-width. If a number is 
    * 		is used here, pixels will be assumed. Otherwise, be sure
    *		to specify a unit. An expression could also be used. 
    * 		By default the value is "auto" which will use an expression 
    * 		to get the border-left-width if it is in pixels.
    * @option String|Number width This is the width of the iframe. If
    *		a number is used here, pixels will be assume. Otherwise, be sure
    * 		to specify a unit. An experssion could also be used.
    *		By default the value is "auto" which will use an experssion
    * 		to get the offsetWidth.
    * @option String|Number height This is the height of the iframe. If
    *		a number is used here, pixels will be assume. Otherwise, be sure
    * 		to specify a unit. An experssion could also be used.
    *		By default the value is "auto" which will use an experssion
    * 		to get the offsetHeight.
    * @option Boolean opacity This is a boolean representing whether or not
    * 		to use opacity. If set to true, the opacity of 0 is applied. If
    *		set to false, the opacity filter is not applied. Default: true.
    * @option String src This setting is provided so that one could change 
    *		the src of the iframe to whatever they need.
    *		Default: "javascript:false;"
    *
    * @name bgiframe
    * @type jQuery
    * @cat Plugins/bgiframe
    * @author Brandon Aaron (brandon.aaron@gmail.com || http://brandonaaron.net)
    */
    $.fn.bgIframe = $.fn.bgiframe = function(s) {
        // This is only for IE6
        if ($.browser.msie && /6.0/.test(navigator.userAgent)) {
            s = $.extend({
                top: 'auto', // auto == .currentStyle.borderTopWidth
                left: 'auto', // auto == .currentStyle.borderLeftWidth
                width: 'auto', // auto == offsetWidth
                height: 'auto', // auto == offsetHeight
                opacity: true,
                src: 'javascript:false;'
            }, s || {});
            var prop = function(n) { return n && n.constructor == Number ? n + 'px' : n; },
		    html = '<iframe class="bgiframe"frameborder="0"tabindex="-1"src="' + s.src + '"' +
		               'style="display:block;position:absolute;z-index:-1;' +
			               (s.opacity !== false ? 'filter:Alpha(Opacity=\'0\');' : '') +
					       'top:' + (s.top == 'auto' ? 'expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\')' : prop(s.top)) + ';' +
					       'left:' + (s.left == 'auto' ? 'expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\')' : prop(s.left)) + ';' +
					       'width:' + (s.width == 'auto' ? 'expression(this.parentNode.offsetWidth+\'px\')' : prop(s.width)) + ';' +
					       'height:' + (s.height == 'auto' ? 'expression(this.parentNode.offsetHeight+\'px\')' : prop(s.height)) + ';' +
					'"/>';
            return this.each(function() {
                if ($('> iframe.bgiframe', this).length == 0)
                    this.insertBefore(document.createElement(html), this.firstChild);
            });
        }
        return this;
    };

})(jQuery);

//jquery.metadata.js
(function($) {

    $.extend({
        metadata: {
            defaults: {
                type: 'class',
                name: 'metadata',
                cre: /({.*})/,
                single: 'metadata'
            },
            setType: function(type, name) {
                this.defaults.type = type;
                this.defaults.name = name;
            },
            get: function(elem, opts) {
                var settings = $.extend({}, this.defaults, opts);
                // check for empty string in single property
                if (!settings.single.length) settings.single = 'metadata';

                var data = $.data(elem, settings.single);
                // returned cached data if it already exists
                if (data) return data;

                data = "{}";

                var getData = function(data) {
                    if (typeof data != "string") return data;

                    if (data.indexOf('{') < 0) {
                        data = eval("(" + data + ")");
                    }
                }

                var getObject = function(data) {
                    if (typeof data != "string") return data;

                    data = eval("(" + data + ")");
                    return data;
                }

                if (settings.type == "html5") {
                    var object = {};
                    $(elem.attributes).each(function() {
                        var name = this.nodeName;
                        if (name.match(/^data-/)) name = name.replace(/^data-/, '');
                        else return true;
                        object[name] = getObject(this.nodeValue);
                    });
                } else {
                    if (settings.type == "class") {
                        var m = settings.cre.exec(elem.className);
                        if (m)
                            data = m[1];
                    } else if (settings.type == "elem") {
                        if (!elem.getElementsByTagName) return;
                        var e = elem.getElementsByTagName(settings.name);
                        if (e.length)
                            data = $.trim(e[0].innerHTML);
                    } else if (elem.getAttribute != undefined) {
                        var attr = elem.getAttribute(settings.name);
                        if (attr)
                            data = attr;
                    }
                    object = getObject(data.indexOf("{") < 0 ? "{" + data + "}" : data);
                }

                $.data(elem, settings.single, object);
                return object;
            }
        }
    });

    /**
    * Returns the metadata object for the first member of the jQuery object.
    *
    * @name metadata
    * @descr Returns element's metadata object
    * @param Object opts An object contianing settings to override the defaults
    * @type jQuery
    * @cat Plugins/Metadata
    */
    $.fn.metadata = function(opts) {
        return $.metadata.get(this[0], opts);
    };

})(jQuery);