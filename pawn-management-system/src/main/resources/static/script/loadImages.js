
function previewImage(url) {
    const img = document.getElementById('imagePreview');
    const placeholder = document.getElementById('placeholderText');
    if (url && url.trim() !== '') {
        img.src = url;
        img.style.display = 'block';
        placeholder.style.display = 'none';
        img.onerror = function() {
            img.style.display = 'none';
            placeholder.style.display = 'block';
        };
    } else {
        img.style.display = 'none';
        placeholder.style.display = 'block';
    }
    window.onload = function() {
        const val = document.getElementById('imageUrlInput').value;
        if (val) previewImage(val);
    };
}
