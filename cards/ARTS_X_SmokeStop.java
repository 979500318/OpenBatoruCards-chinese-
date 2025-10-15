package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_X_SmokeStop extends Card {

    public ARTS_X_SmokeStop()
    {
        setImageSets("WX24-P1-033");

        setOriginalName("スモーク・ストップ");
        setAltNames("スモークストップ Sumooku Sutoppu");
        setDescription("jp",
                "対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Smoke Stop");
        setDescription("en",
                "Target 1 of your opponent's LRIG, and until end of turn, it gains:@>@C: Can't attack."
        );

		setName("zh_simplified", "恶瘴·阻碍");
        setDescription("zh_simplified", 
                "对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}

