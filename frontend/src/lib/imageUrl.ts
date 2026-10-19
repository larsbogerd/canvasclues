// Templates carry a {size} placeholder and point at our own origin (/img/<source>/), which
// nginx and the Vite dev server proxy upstream. Non-IIIF sources (cma, met) ignore the size.
//
// The board tile and the art-info panel must ask for the same size: each IIIF size is a
// separate derivative the museum renders cold on first request.
export const CARD_IMAGE_SIZE = '500,'

export function imageUrl(
    template: string | null | undefined,
    size = CARD_IMAGE_SIZE,
): string {
    return template ? template.replace('{size}', size) : ''
}
