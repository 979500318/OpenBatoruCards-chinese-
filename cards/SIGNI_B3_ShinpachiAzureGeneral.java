package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_ShinpachiAzureGeneral extends Card {
    
    public SIGNI_B3_ShinpachiAzureGeneral()
    {
        setImageSets("WXDi-D05-016");
        
        setOriginalName("蒼将　シンパチ");
        setAltNames("ソウショウシンパチ Soushou Shinpachi");
        setDescription("jp",
                "@E %B：カードを１枚引く。\n" +
                "@A $T1 %B %B %B：対戦相手のシグニ１体を対象とし、あなたの手札が対戦相手より４枚以上多い場合、それをバニッシュする。" +
                "~#：カードを１枚引く。対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Shimpachi, Azure General");
        setDescription("en",
                "@E %B: Draw a card.\n" +
                "@A $T1 %B %B %B: Vanish target SIGNI on your opponent's field if you have four or more cards in your hand than your opponent." +
                "~#Draw a card. Your opponent discards a card."
        );
        
        setName("en_fan", "Shinpachi, Azure General");
        setDescription("en_fan",
                "@E %B: Draw 1 card.\n" +
                "@A $T1 %B %B %B: Target 1 of your opponent's SIGNI, and if there are at least 4 more cards in your hand than your opponent's, banish it." +
                "~#Draw 1 card. Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "苍将 永仓新八");
        setDescription("zh_simplified", 
                "@E %B:抽1张牌。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n" +
                "@A $T1 %B %B %B:对战对手的精灵1只作为对象，你的手牌比对战对手多4张以上的场合，将其破坏。" +
                "~#抽1张牌。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 3)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            if(target != null && (getHandCount(getOwner()) - getHandCount(getOpponent())) >= 4)
            {
                banish(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            discard(getOpponent(), 1);
        }
    }
}
