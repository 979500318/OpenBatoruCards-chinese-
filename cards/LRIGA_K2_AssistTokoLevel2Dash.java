package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_AssistTokoLevel2Dash extends Card {
    
    public LRIGA_K2_AssistTokoLevel2Dash()
    {
        setImageSets("WXDi-P00-031");
        
        setOriginalName("【アシスト】とこ　レベル２’");
        setAltNames("アシストとこレベルニダッシュ Ashisuto Toko Reberu Ni Dasshu Dash Assist Toko");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。\n" +
                "@E %W：あなたのトラッシュから白のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "[Assist] Toko, Level 2'");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --12000 power until end of turn.\n" +
                "@E %W: Add target white SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "[Assist] Toko Level 2'");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, that SIGNI gets --12000 power.\n" +
                "@E %W: Target 1 white SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "【支援】床 等级2'");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，将其的力量-12000。\n" +
                "@E %W:从你的废弃区把白色的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(cardIndex, -12000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.WHITE).fromTrash()).get();
            addToHand(cardIndex);
        }
    }
}