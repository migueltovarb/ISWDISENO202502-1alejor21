export const LoadingSpinner = ({ size = 'medium' }) => {
  const sizeClasses = {
    small: 'spinner-small',
    medium: 'spinner',
    large: 'spinner-large'
  }

  return (
    <div className="loading-container">
      <div className={sizeClasses[size]}></div>
    </div>
  )
}
