import { Input } from '@/components/ui/input'

export function StepTwo() {
  return (
    <div>
      <h3 className="text-base font-medium">
        Schritt 2 — Tenant Configuration
      </h3>

      <div className="mt-4 space-y-2">
        <div className="flex w-full gap-2">
          <div className="flex-1 min-w-0">
            <Input placeholder="Firstname" className="w-full" />
          </div>
          <div className="flex-1 min-w-0">
            <Input placeholder="Lastname" className="w-full" />
          </div>
        </div>

        <Input className="w-full" type="email" placeholder="E-Mail" />
        <Input
          className="w-full"
          type="password"
          placeholder="Enter your password"
        />
        <Input
          className="w-full"
          type="password"
          placeholder="Confirm password"
        />
      </div>
    </div>
  )
}

export default StepTwo
