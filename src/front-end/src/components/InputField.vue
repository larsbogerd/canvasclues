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
    <div class="fields">
      <div class="input-field" :class="{ 'has-value': modelValue.length > 0 }">
        <input class="text-jetbrainsMono input-field_input" :readonly="readonly" :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)" :disabled="InputDisabled">
        <label class="input-field_label">
          {{ props.label }}
        </label>
      </div>
      <button class="text-newAmsterdam" type="submit" @click="buttonClick" :disabled="modelValue ===''">
      {{ name }}
      </button>
    </div>
  </div>
</template>

<style scoped>

.text-field {
  width: 25.625rem;
  height: 6.625rem;
  background: var(--primary-color);
  border-radius: 30px;
  box-shadow: 4px 4px 4px 4px var(--primary-shadow);
}

.fields {
  box-sizing: border-box;
  padding: 25px;
  height: 3.438rem;
  width: 25.625rem;
  display: flex;
  flex-direction: row;
  column-gap: 12px;
}

.fields input {
  padding: 0px 0px 0px 10px;
  background: var(--background-color);
  border: none;
  border-radius: 20px;
  height: 3.438rem;
  width: 13.188rem;
}

.fields button {
  background: var(--background-color);
  border: none;
  height: 3.438rem;
  width: 7.75rem;
  border-radius: 20px;
}

.input-field{
  position: relative;
}
.input-field_input {
  padding: 0px 0px 0px 10px;
  background: var(--background-color);
  border: none;
  border-radius: 20px;
  height: 3.438rem;
  width: 13.188rem;
  cursor: text;
  outline: none;
}


.input-field_label{
  position: absolute;
  top: 0;
  left: 0;
  transform: translate(15px,15px);
  transition: opacity 200ms;
}

.input-field input:focus ~ .input-field_label,
.input-field.has-value .input-field_label{
  opacity: 0;

}

</style>