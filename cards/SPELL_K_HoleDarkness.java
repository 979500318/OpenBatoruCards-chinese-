package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_K_HoleDarkness extends Card {

    public SPELL_K_HoleDarkness()
    {
        setImageSets("WX24-D5-25");

        setOriginalName("ホール・ダークネス");
        setAltNames("ホールダークネス Hooru Daakunesu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－7000する。&E５枚以上@0追加で対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Hole Darkness");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --7000 power. &E5 or more@0 Additionally, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "乌黑·之洞");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-7000。&E5张以上@0追加对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff).setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -7000, ChronoDuration.turnEnd());
            
            if(getAbility().isRecollectFulfilled())
            {
                target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
