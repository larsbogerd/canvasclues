// Display details per art source, keyed like Artwork.source / CardResponse.source on the
// back-end. Adding a source there means adding it here too.
export const ART_SOURCES = {
    artic: {name: 'Art Institute of Chicago', url: 'https://www.artic.edu'},
    cma: {name: 'Cleveland Museum of Art', url: 'https://www.clevelandart.org'},
    met: {name: 'The Metropolitan Museum of Art', url: 'https://www.metmuseum.org'},
};

export const ART_SOURCE_KEYS = Object.keys(ART_SOURCES);
