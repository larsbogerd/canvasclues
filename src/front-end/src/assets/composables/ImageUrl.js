// Artwork image URLs.
//
// The back-end hands out a template with a {size} placeholder, e.g.
// "/iiif/2/<image-id>/full/{size}/0/default.jpg". Which source it came from and which
// IIIF version that source speaks is its problem, not ours -- we only pick a size.
//
// Templates deliberately point at our own origin: nginx (prod) and the Vite dev server
// (local) proxy upstream and add the Referer header that Artic's Cloudflare demands.
// Hotlinking straight from the browser gets a 403 challenge page instead of a JPEG.

// `size` is a IIIF size spec, e.g. "600," (width 600, height auto) or
// "!500,500" (fit inside a 500x500 box).
// The board tile and the art-info panel must ask for the same size: for IIIF sources each
// size is a separate derivative the museum renders on first request, so two sizes meant two
// cold renders per artwork, and the panel could finish before the tile it belongs to.
// ArticClient.warmImage pre-renders this size for pooled artworks; change both together.
export const CARD_IMAGE_SIZE = '500,';

export function imageUrl(template, size) {
    return template ? template.replace('{size}', size) : '';
}
