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

public final class ARTS_K_ThousandEyesPunish extends Card {

    public ARTS_K_ThousandEyesPunish()
    {
        setImageSets("WX24-D5-07");

        setOriginalName("サウザンドアイ・パニッシュ");
        setAltNames("サウザンドアイパニッシュ Sauzando Ai Panishhu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "&E４枚以上@0代わりにターン終了時まで、それのパワーを－20000する。"
        );

        setName("en", "Thousand-Eyes Punish");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "&E4 or more@0 Instead, target 1 of your opponent's SIGNI, and until end of turn, it gets --20000 power."
        );

        setName("zh_simplified", "千之终焉·惩戒");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "&E4张以上@0作为替代，直到回合结束时为止，其的力量-20000。\n" +
                "（你的分身废弃区有4张以上的必杀时，则&E4张以上@0后的文字变为有效）"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, !getAbility().isRecollectFulfilled() ? -8000 : -20000, ChronoDuration.turnEnd());
        }
    }
}

