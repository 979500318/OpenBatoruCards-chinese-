package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_MurmurnAzureDevil extends Card {

    public SIGNI_B1_MurmurnAzureDevil()
    {
        setImageSets("WXDi-P16-072");

        setOriginalName("蒼魔　ムルムルン");
        setAltNames("ソウマムルムルン Souma Murumurun");
        setDescription("jp",
                "@U：このカードがコストか効果によって捨てられたとき、%Xを支払ってもよい。そうした場合、カードを１枚引く。"
        );

        setName("en", "Murmurn, Azure Evil");
        setDescription("en",
                "@U: When this card is discarded by a cost or an effect, you may pay %X. If you do, draw a card."
        );
        
        setName("en_fan", "Murmurn, Azure Devil");
        setDescription("en_fan",
                "@U: When this card is discarded by a cost or an effect, you may pay %X. If you do, draw 1 card."
        );

		setName("zh_simplified", "苍魔 毛莫");
        setDescription("zh_simplified", 
                "@U :当这张牌因为费用或效果舍弃时，可以支付%X。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(payEner(Cost.colorless(1)))
            {
                draw(1);
            }
        }
    }
}
