//

function goTo(url) {
  window.location.href = url;
}

function doDelete(message, url) {
  if (confirm(message)) {
    goTo(url);
  }
}

function getSafeValue(value) {
  if (value == null || value == "") { return "&nbsp;"; }
  return value;
}

function getClearValue(value) {
  if (value == "&nbsp;") { return ""; }
  return value;
}

function updateTips(t) {
  tips.text(t).addClass("ui-state-highlight");
  tips.fadeIn();
  setTimeout(function() {
    tips.removeClass("ui-state-highlight");
    tips.fadeOut();
  }, 3000);
}

function checkLength(o, n, min, max) {
  if (o.val().length > max || o.val().length < min) {
    o.addClass("ui-state-error");
    updateTips("ความยาวของ " + n + " จะต้องอยู่ระหว่าง " + min + " ถึง " + max
        + " เท่านั้น");
    return false;
  } else {
    return true;
  }
}

function checkRegexp(o, regexp, n) {
  if (!(regexp.test(o.val()))) {
    o.addClass("ui-state-error");
    updateTips(n);
    return false;
  } else {
    return true;
  }
}
