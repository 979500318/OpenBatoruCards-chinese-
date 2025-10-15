package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class ARTS_KR_CrimsonReborn extends Card {

    public ARTS_KR_CrimsonReborn()
    {
        setImageSets("WX24-P4-010","WX24-P4-010U");

        setOriginalName("クリムゾン・リボーン");
        setAltNames("クリムゾンリボーン Kurimuzon Riboon");
        setDescription("jp",
                "あなたのトラッシュからシグニを２枚まで対象とし、それらを場に出す。その後、あなたのシグニ１体を対象とし、ターン終了時まで、それは【アサシン】か【ダブルクラッシュ】を得る。"
        );

        setName("en", "Crimson Reborn");
        setDescription("en",
                "Target up to 2 SIGNI from your trash, and put them onto the field. Then, target 1 of your SIGNI, and until end of turn, it gains [[Assassin]] or [[Double Crush]]."
        );

		setName("zh_simplified", "血涂·复活");
        setDescription("zh_simplified", 
                "从你的废弃区把精灵2张最多作为对象，将这些出场。然后，你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]或[[双重击溃]]。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK, CardColor.RED);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable());
            putOnField(data);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, playerChoiceAction(ActionHint.ASSASSIN, ActionHint.DOUBLECRUSH) == 1 ? new StockAbilityAssassin() : new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
