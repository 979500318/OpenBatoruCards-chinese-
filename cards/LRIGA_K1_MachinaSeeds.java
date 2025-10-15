package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_MachinaSeeds extends Card {
    
    public LRIGA_K1_MachinaSeeds()
    {
        setImageSets("WXDi-P04-025");
        
        setOriginalName("マキナシーズ");
        setAltNames("Makina Shiizu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－7000する。\n" +
                "@U：このカードがエクシードのコストとしてルリグトラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );
        
        setName("en", "Machina Seeds");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --7000 power until end of turn.\n\n" +
                "@U: When this card is put into the LRIG Trash as an Exceed cost, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Machina Seeds");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --7000 power.\n" +
                "@U: When this card is put into your LRIG trash for an @[Exceed]@ cost, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );
        
		setName("zh_simplified", "玛琪娜种子");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-7000。\n" +
                "@U :当这张牌作为超越的费用放置到分身废弃区时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerAutoAbility(GameEventId.EXCEED, this::onAutoEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -7000, ChronoDuration.turnEnd());
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
