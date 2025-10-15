package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class LRIGA_K2_IonaCrisis extends Card {

    public LRIGA_K2_IonaCrisis()
    {
        setImageSets("WX24-P3-046");

        setOriginalName("イオナ・クライシス");
        setAltNames("イオナクライシス Iona Kuraishisu");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニを２枚まで対象とし、それらを場に出す。\n" +
                "@E：あなたの場に白と黒のシグニがある場合、ターン終了時まで、対戦相手のすべてのシグニのパワーを－5000する。"
        );

        setName("en", "Iona Crisis");
        setDescription("en",
                "@E: Target up to 2 SIGNI from your trash, and put them onto the field.\n" +
                "@E: If there is a black and a white SIGNI on your field, until end of turn, all of your opponent's SIGNI get --5000 power."
        );

		setName("zh_simplified", "伊绪奈·危机");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把精灵2张最多作为对象，将这些出场。\n" +
                "@E :你的场上有白色和黑色的精灵的场合，直到回合结束时为止，对战对手的全部的精灵的力量-5000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter().own().SIGNI().fromTrash().playable());
            putOnField(data);
        }
        
        private void onEnterEff2()
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.BLACK).getValidTargetsCount() > 0 &&
               new TargetFilter().own().SIGNI().withColor(CardColor.WHITE).getValidTargetsCount() > 0)
            {
                gainPower(getSIGNIOnField(getOpponent()), -5000, ChronoDuration.turnEnd());
            }
        }
    }
}
