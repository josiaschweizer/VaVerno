import { Field, FieldDescription, FieldLabel } from '@/components/ui/field'
import { Input } from '@/components/ui/input'
import { HTMLInputTypeAttribute } from 'react'

interface Props {
  fieldLabel?: string
  placeholder?: string
  fieldDescription?: string
  type?: HTMLInputTypeAttribute
  fieldId?: string
}

export function InputField({
  fieldLabel,
  placeholder,
  fieldDescription,
  type = 'text',
  fieldId,
}: Props) {
  return (
    <Field>
      <FieldLabel htmlFor={fieldId}>{fieldLabel}</FieldLabel>
      <Input id={fieldId} type={type} placeholder={placeholder} />
      <FieldDescription>{fieldDescription}</FieldDescription>
    </Field>
  )
}
