package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B2_SnowsecPhantomInsect extends Card {

    public SIGNI_B2_SnowsecPhantomInsect()
    {
        setImageSets("WXDi-P11-068");

        setOriginalName("幻蟲　スノセク");
        setAltNames("ゲンチュウスノセク Genchuu Sunoseku");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにあなたがカードを２枚以上捨てていた場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。\n" +
                "@E：カードを１枚引き、手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Snowsec, Phantom Insect");
        setDescription("en",
                "@U: At the end of your turn, if you have discarded two or more cards this turn, this SIGNI gets +4000 power until the end of your opponent's next end phase.\n" +
                "@E: Draw a card and discard a card." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Snowsec, Phantom Insect");
        setDescription("en_fan",
                "@U: At the end of your turn, if you discarded 2 or more cards from your hand this turn, until the end of your opponent's next turn, this SIGNI gets +4000 power.\n" +
                "@E: Draw 1 card, and discard 1 card from your hand." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "幻虫 雪虫");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合你把牌2张以上舍弃过的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n" +
                "@E :抽1张牌，手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 2)
            {
                gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onEnterEff()
        {
            draw(1);
            discard(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}

