package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_HyacinthNaturalPlant extends Card {
    
    public SIGNI_B1_HyacinthNaturalPlant()
    {
        setImageSets("WXDi-P07-071", "SPDi38-17");
        
        setOriginalName("羅植　ヒヤシンス");
        setAltNames("ラショクヒヤシンス Rashoku Hiyashinsu");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：このターン終了時、【エナチャージ１】をする。" +
                "~#：カードを２枚引き【エナチャージ１】をする。"
        );
        
        setName("en", "Hyacinth, Natural Plant");
        setDescription("en",
                "@E @[Discard a card]@: At the end of this turn, [[Ener Charge 1]]." +
                "~#Draw two cards and [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Hyacinth, Natural Plant");
        setDescription("en_fan",
                "@E @[Discard 1 card from your hand]@: At the end of this turn, [[Ener Charge 1]]." +
                "~#Draw 2 cards, and [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "罗植 风信子");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:这个回合结束时，[[能量填充1]]。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）" +
                "~#抽2张牌并[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                enerCharge(1);
            });
        }
        
        private void onLifeBurstEff()
        {
            draw(2);
            enerCharge(1);
        }
    }
}
