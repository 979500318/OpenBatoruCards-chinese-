package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_VinylSharkFirstPlay extends Card {

    public SIGNI_B1_VinylSharkFirstPlay()
    {
        setImageSets("WX25-P2-083");

        setOriginalName("壱ノ遊　ビニールサメ");
        setAltNames("イチノユウビニールサメ Ichi no Yuu Biniirusame");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにあなたの効果によってこのシグニが場に出ていた場合、カードを１枚引く。"
        );

        setName("en", "Vinyl Shark, First Play");
        setDescription("en",
                "@U: At the end of your turn, if this SIGNI entered the field by your effect this turn, draw 1 card."
        );

		setName("zh_simplified", "壹之游 充气鲨鱼");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合因为你的效果把这只精灵出场的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ENTER && event.getCaller().getInstanceId() == getInstanceId() && event.getSourceAbility() != null && isOwnCard(event.getSource())) > 0)
            {
                draw(1);
            }
        }
    }
}
