<script setup>
// The Art Institute of Chicago has at times put every image request behind a
// Cloudflare challenge, which the /iiif/ proxy (nginx.conf) cannot pass.
</script>

<template>
  <aside class="service-notice" role="status">
    <p class="notice-title">Laden de afbeeldingen niet?</p>
    <p class="notice-body">
      De kunstwerken komen van het Art Institute of Chicago, dat sinds kort
      verzoeken naar hun afbeeldingen blokkeert. Ik wacht op een oplossing of
      reactie van hun kant, en zoek ondertussen naar een andere bron.
    </p>
  </aside>
</template>

<style scoped>
.service-notice {
  padding: 0.85rem 1rem;
  border-radius: 6px;
  background-color: var(--bg-light-opacity);
  box-shadow: 0 8px 20px var(--tertiary-shadow);
}

.notice-title {
  margin: 0 0 0.25rem;
  font-family: var(--font-secondary), sans-serif;
  font-size: 1rem;
  font-weight: 600;
  color: var(--text-primary);
}

.notice-body {
  margin: 0;
  font-family: var(--font-secondary), sans-serif;
  font-size: 0.9rem;
  line-height: 1.45;
  color: var(--text-secondary);
}
</style>
