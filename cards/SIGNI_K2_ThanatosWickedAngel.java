package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;

public final class SIGNI_K2_ThanatosWickedAngel extends Card {
    
    public SIGNI_K2_ThanatosWickedAngel()
    {
        setImageSets("WXDi-P07-090");
        
        setOriginalName("凶天　タナトス");
        setAltNames("キョウテンタナトス Kyouten Tanatosu");
        setDescription("jp",
                "@E %K：対戦相手のシグニ１体を対象とし、あなたのトラッシュからそれぞれレベルの異なる＜天使＞のシグニ３枚をデッキに加えてシャッフルする。そうした場合、ターン終了時まで、それのパワーを－5000する。"
        );
        
        setName("en", "Thanatos, Doomed Angel");
        setDescription("en",
                "@E %K: Shuffle three <<Angel>> SIGNI with different levels from your trash into your deck. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Thanatos, Wicked Angel");
        setDescription("en_fan",
                "@E %K: Target 1 of your opponent's SIGNI, and shuffle 3 <<Angel>> SIGNI with different levels from your trash into your deck. If you do, until end of turn, it gets --5000 power."
        );
        
		setName("zh_simplified", "凶天 塔纳托斯");
        setDescription("zh_simplified", 
                "@E %K:对战对手的精灵1只作为对象，从你的废弃区把等级不同的<<天使>>精灵3张加入牌组洗切。这样做的场合，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash();
                if(filter.getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().count() >= 3)
                {
                    DataTable<CardIndex> data = playerTargetCard(3, filter, this::onEnterEffTargetCond);
                    
                    if(returnToDeck(data, DeckPosition.TOP) == 3 && shuffleDeck())
                    {
                        gainPower(target, -5000, ChronoDuration.turnEnd());
                    }
                }
            }
        }
        private boolean onEnterEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).distinct().count() == 3;
        }
    }
}
