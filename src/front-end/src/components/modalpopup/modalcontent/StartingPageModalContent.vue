<template>
  <div class="container">
    <section>
      <h2 class="text-color">Introductie Canvas Clues</h2>
      <p class="text-color">
        Canvas Clues is een spel gebaseerd op het populaire spel Codenames,
        maar dan in een museum vol kunst, geheimen en verborgen verbanden.
      </p>
    </section>

    <section>
      <h3 class="text-color">De twee rollen</h3>
      <p class="text-color">
        In Canvas Clues kies je in de hub tussen twee rollen: Curator en Bezoeker.
      </p>
    </section>

    <section>
      <h3 class="text-color">Curator</h3>
      <p class="text-color">
        Als Curator is het jouw uitdaging om slimme hints te bedenken voor de
        geselecteerde kunststukken op het bord.
        <br><br>
        Kun jij meesterlijke verbanden leggen tussen kunst en woorden?
      </p>
    </section>

    <section>
      <h3 class="text-color">Bezoeker</h3>
      <p class="text-color">
        Als Bezoeker is het jouw uitdaging om te raden welke kunststukken bij de
        hints van de Curator horen.
        <br><br>
        Zie jij snel patronen en ontdek jij verborgen connecties?
      </p>
    </section>
  </div>
</template>

<style scoped>
p{
  font-family: var(--font-secondary),sans-serif;
  font-weight: 300;
}
.text-color{
  color: var(--text-primary);
}
.container{
  width: 28vw;
  padding: clamp(24px, 3vw, 48px) clamp(32px, 4vw, 64px);
}
</style>