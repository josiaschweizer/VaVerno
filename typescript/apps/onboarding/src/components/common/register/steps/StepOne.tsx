import { Input } from '@/components/ui/input'
import RegisterDialogFormData from '@/interfaces/register/RegisterDialogFormData'
import { Control, Controller } from 'react-hook-form'

interface Props {
  control: Control<RegisterDialogFormData>
  getValues: (field: string) => any
  readOnly: boolean
}

export default function StepOne({ control, getValues, readOnly }: Props) {
  const validatePasswordMatch = (value: string) => {
    return value === getValues('password') || 'Passwords do not match'
  }

  return (
    <div>
      <h3 className="text-base font-medium">
        Schritt 1 — Basic Data for Your Account
      </h3>

      <div className="mt-4 space-y-2">
        <div className="flex w-full gap-2">
          <div className="flex-1 min-w-0">
            <Controller
              name="firstname"
              control={control}
              render={({ field: { onChange, value } }) => (
                <Input
                  placeholder="Firstname"
                  value={value}
                  onChange={onChange}
                  disabled={readOnly}
                  className="w-full"
                />
              )}
            />
          </div>
          <div className="flex-1 min-w-0">
            <Controller
              name="lastname"
              control={control}
              render={({ field: { onChange, value } }) => (
                <Input
                  placeholder="Lastname"
                  value={value}
                  onChange={onChange}
                  disabled={readOnly}
                  className="w-full"
                />
              )}
            />
          </div>
        </div>

        <Controller
          name="email"
          control={control}
          render={({ field: { onChange, value } }) => (
            <Input
              placeholder="E-Mail"
              value={value}
              onChange={onChange}
              disabled={readOnly}
              className="w-full"
            />
          )}
        />

        <Controller
          name="password"
          control={control}
          rules={{
            required: 'Password is required',
            minLength: {
              value: 8,
              message: 'Password must be at least 8 characters',
            },
          }}
          render={({ field, fieldState }) => (
            <Input
              {...field}
              type="password"
              placeholder="Enter your password"
              className="w-full"
            />
          )}
        />

        <Controller
          name="confirmPassword"
          control={control}
          rules={{
            required: 'Please confirm your password',
            validate: validatePasswordMatch,
          }}
          render={({ field, fieldState }) => (
            <div>
              <Input
                {...field}
                type="password"
                placeholder="Confirm your password"
                className={`w-full ${fieldState.error ? 'border-red-500' : ''}`}
              />

              {fieldState.error && (
                <p className="mt-1 text-sm text-red-500">
                  {fieldState.error.message}
                </p>
              )}
            </div>
          )}
        />
      </div>
    </div>
  )
}
