package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class LRIGA_R2_LaylaTheRoaringStrength extends Card {

    public LRIGA_R2_LaylaTheRoaringStrength()
    {
        setImageSets("WXDi-P12-029");

        setOriginalName("レイラ・ザ・轟健");
        setAltNames("レイラザゴウケン Reira Za Gouken");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それは【アサシン】と【ダブルクラッシュ】を得る。"
        );

        setName("en", "Layla the Roaring Vigor");
        setDescription("en",
                "@E: Target SIGNI on your field gains [[Assassin]] and [[Double Crush]] until end of turn.\n"
        );
        
        setName("en_fan", "Layla the Roaring Strength");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and until end of turn, it gains [[Assassin]] and [[Double Crush]]."
        );

		setName("zh_simplified", "蕾拉·极·轰健");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]和[[双重击溃]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            
            if(target != null)
            {
                attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
                attachAbility(target, new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
            }
        }
    }
}
