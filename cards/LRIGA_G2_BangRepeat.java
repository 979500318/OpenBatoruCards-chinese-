package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_G2_BangRepeat extends Card {
    
    public LRIGA_G2_BangRepeat()
    {
        setImageSets("WXDi-D05-010");
        
        setOriginalName("バン＝リピート");
        setAltNames("バンリピート Ban Ripiito");
        setDescription("jp",
                "@E：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Bang =Repeat=");
        setDescription("en",
                "@E: Add target SIGNI from your Ener Zone to your hand.\n" +
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or more."
        );
        
        setName("en_fan", "Bang-Repeat");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@E: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );
        
		setName("zh_simplified", "梆=重复");
        setDescription("zh_simplified", 
                "@E :从你的能量区把精灵1张作为对象，将其加入手牌。\n" +
                "@E :对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
