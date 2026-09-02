// Artwork image URLs.
//
// These deliberately point at our own origin, not at www.artic.edu: nginx (prod)
// and the Vite dev server (local) proxy /iiif/ upstream and add the Referer
// header that Artic's Cloudflare now demands. Hotlinking straight from the
// browser gets a 403 challenge page instead of a JPEG.
const IIIF_BASE = '/iiif/2';

// `size` is a IIIF size spec, e.g. "600," (width 600, height auto) or
// "!500,500" (fit inside a 500x500 box).
export function iiifUrl(imageId, size) {
    return `${IIIF_BASE}/${imageId}/full/${size}/0/default.jpg`;
}
