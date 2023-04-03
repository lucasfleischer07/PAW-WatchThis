import React from 'react';
import { OverlayTrigger, Tooltip } from 'react-bootstrap';

const TooltipComponent = ({ text, children }) => {
    return (
        <OverlayTrigger
            placement="bottom"
            overlay={<Tooltip>{text}</Tooltip>}
        >
            {children}
        </OverlayTrigger>
    );
};

export default TooltipComponent;
