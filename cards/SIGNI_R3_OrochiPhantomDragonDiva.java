package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_OrochiPhantomDragonDiva extends Card {

    public SIGNI_R3_OrochiPhantomDragonDiva()
    {
        setImageSets("WXDi-P10-037", "SPDi10-17");

        setOriginalName("幻竜歌姫　オロチ");
        setAltNames("ゲンリュウウタヒメオロチ Genryuu Utahime Orochi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが４枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );

        setName("en", "Orochi, Phantom Dragon Diva");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are four or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@U: Whenever this SIGNI attacks, if there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash."
        );
        
        setName("en_fan", "Orochi, Phantom Dragon Diva");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 4 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "@U: Whenever this SIGNI attacks, if there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash."
        );

		setName("zh_simplified", "幻龙歌姬 大蛇");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在4张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@U :当这只精灵攻击时，对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 4)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
        
        private void onAutoEff2()
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
    }
}
