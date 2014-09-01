(function($) {
	$.stickyInfo = function(note, options, callback) {
		return $.fn.stickyInfo(note, options, callback)
	};
	$.fn.stickyInfo = function(note, options, callback) {
		var position = "top-center";
		var settings = {
			speed: "fast",
			duplicates: true,
			autoclose: 10000
		};
		if (!note) {
			note = this.html()
		}
		if (options) {
			$.extend(settings, options)
		}
		var display = true;
		var duplicate = "no";
		var uniqID = Math.floor(Math.random() * 99999);
		$(".sticky-note").each(function() {
			if ($(this).html() == note && $(this).is(":visible")) {
				duplicate = "yes";
				if (!settings.duplicates) {
					display = false
				}
			}
			if ($(this).attr("id") == uniqID) {
				uniqID = Math.floor(Math.random() * 9999999)
			}
		});
		if (!$("body").find(".sticky-queue").html()) {
			$("body").append('<div class="sticky-queue ' + position + '"></div>')
		}
		if (display) {
			$(".sticky-queue").prepend('<div class="sticky border-' + position + '" id="' + uniqID + '"></div>');
			$("#" + uniqID).append('<img src="../static/js/sticky-info/close.png" class="sticky-close" rel="' + uniqID + '" title="Close" />');
			$("#" + uniqID).append('<div class="sticky-note" rel="' + uniqID + '">' + note + "</div>");
			//var height = $("#" + uniqID).height();
			//$("#" + uniqID).css("height", height);
			$("#" + uniqID).slideDown(settings.speed);
			display = true
		}
		$(".sticky").ready(function() {
			if (settings.autoclose) {
				$("#" + uniqID).delay(settings.autoclose).fadeOut(settings.speed)
			}
		});
		$(".sticky-close").click(function() {
			$("#" + $(this).attr("rel")).dequeue().fadeOut(settings.speed)
		});
		var response = {
			id: uniqID,
			duplicate: duplicate,
			displayed: display,
			position: position
		};
		if (callback) {
			callback(response)
		} else {
			return (response)
		}
	}
})(jQuery);