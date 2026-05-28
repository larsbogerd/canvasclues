<script setup>

const props = defineProps({
  label: String,
  modelValue: String,
  name: String,
  readonly: Boolean,
  InputDisabled: Boolean,
})

const emit = defineEmits(["submit", "update:modelValue"])


function buttonClick() {
  emit('submit', props.modelValue);
}

</script>
<template>
  <div class="text-field">
    <form class="fields" @submit.prevent="buttonClick">
    <div class="fields">
      <div class="input-field"
           :class="{ 'has-value': (modelValue?.length ?? 0) > 0 }">

        <input class="text-jetbrainsMono input-field_input"
               :readonly="readonly"
               :value="modelValue"
               @input="$emit('update:modelValue', $event.target.value)"
               :disabled="InputDisabled">

        <label class="input-field_label">
          {{ props.label }}
        </label>

      </div>
      <button class="submit-btn"
              type="submit"
              @click="buttonClick">
        {{ name }}
      </button>
    </div>
  </form>
  </div>

</template>

<style scoped>

.text-field {
  width: 100%;
  font-family: var(--font-secondary),sans-serif;
}

.fields {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.input-field {
  position: relative;
}

.input-field_input {
  box-sizing: border-box;
  width: 100%;
  padding: 12px 16px;
  font-family: var(--font-secondary),sans-serif;
  background: var(--color-secondary);
  border: 1px solid var(--secondary-border);
  border-radius: 20px;
  height: 3rem;
  cursor: text;
  outline: none;
}

.input-field_input:focus {
  border-color: var(--color-primary);
  color: var(--text-primary);
}

.input-field_label {
  position: absolute;
  top: 0;
  left: 0;
  transform: translate(16px, 12px);
  transition: opacity 180ms ease;
  color: var(--text-placeholder);
  pointer-events: none;
}

.input-field input:focus ~ .input-field_label,
.input-field.has-value .input-field_label {
  opacity: 0;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  font-family: var(--font-secondary), sans-serif;
  color: var(--color-bg-base);
  background-color: var(--color-primary);
  border: none;
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 10px 20px color-mix(in srgb, var(--color-primary) 28%, transparent);
  transition: transform 180ms ease, box-shadow 180ms ease, filter 180ms ease;
}

.submit-btn:hover,
.submit-btn:focus-visible {
  filter: saturate(1.02);
  box-shadow: 0 14px 24px color-mix(in srgb, var(--color-primary) 38%, transparent);
}

</style>
