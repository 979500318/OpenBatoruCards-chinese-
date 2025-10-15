package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_AssistAngeLevel2DoubleDash extends Card {
    
    public LRIGA_W2_AssistAngeLevel2DoubleDash()
    {
        setImageSets("WXDi-P00-029");
        
        setOriginalName("【アシスト】アンジュ　レベル２”");
        setAltNames("アシストアンジュレベルニダブルダッシュ Ashisuto Anju Reberu Ni Daburu Dasshu Double Dash Assist Ange");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@E %K：あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "[Assist] Ange, Level 2''");
        setDescription("en",
                "@E: Return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@E %K: Add target black SIGNI form your trash to your hand."
        );
        
        setName("en_fan", "[Assist] Ange Level 2''");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@E %K: Target 1 black SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "【支援】安洁 等级2''");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@E %K:从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(cardIndex);
        }
        
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(cardIndex);
        }
    }
}
