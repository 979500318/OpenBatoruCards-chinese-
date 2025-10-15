package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_AkinoByeBye extends Card {
    
    public LRIGA_W2_AkinoByeBye()
    {
        setImageSets("WXDi-P01-011");
        
        setOriginalName("アキノ＊バイバイ");
        setAltNames("アキノバイバイ Akino Baibai");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E %W %X：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Akino*Bye - Bye!");
        setDescription("en",
                "@E: Return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@E %W %X: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Akino*Bye-Bye");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@E %W %X: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "昭乃＊拜拜");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@E %W%X从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
