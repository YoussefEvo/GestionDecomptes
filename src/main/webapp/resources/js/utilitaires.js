jQuery(document).ready(function(){
	if (window.history && window.history.pushState) {
		desactiveLoading();
	}
});
function activeLoading() {
  jQuery("body").addClass("loading");
};

function desactiveLoading() {
  jQuery("body").removeClass("loading");
};