package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_CrystalShield extends Card {

    public ARTS_W_CrystalShield()
    {
        setImageSets("WX24-P4-027");

        setOriginalName("クロス・ソード");
        setAltNames("クロスソード Kurosu Soodo");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のルリグかシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "$$2対戦相手の赤か黒のシグニ１体を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Crystal Shield");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIG or SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "$$2 Target 1 of your opponent's red or black SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "水晶·之盾");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的分身或精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "$$2 对战对手的红色或黑色的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.ATTACK);

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
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withColor(CardColor.RED, CardColor.BLACK)).get();
                trash(target);
            }
        }
    }
}
