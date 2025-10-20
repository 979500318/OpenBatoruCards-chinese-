package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_PalamedesCrimsonGeneral extends Card {

    public SIGNI_R2_PalamedesCrimsonGeneral()
    {
        setImageSets("SPDi01-122");

        setOriginalName("紅将　パロミデ");
        setAltNames("コウショウパロミデ Koushou Paromide");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが２枚以上ある場合、あなたのアップ状態のルリグ１体をダウンしてもよい。その後、対戦相手のエナゾーンからこの方法でダウンしたルリグと共通する色を持つカード１枚を対象とし、それをトラッシュに置く。"
        );

        setName("en", "Palamedes, Crimson General");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are 2 or more cards in your opponent's ener zone, you may down 1 of your upped LRIG. Then, target 1 card from your opponent's ener zone that shares a common color with the LRIG downed this way, and put it into the trash."
        );

		setName("zh_simplified", "红将 帕拉梅德斯");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在2张以上的场合，可以把你的竖直状态的分身1只横置。然后，从对战对手的能量区把持有与这个方法横置的分身共通颜色的牌1张作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.DOWN).own().anyLRIG().upped()).get();
                
                if(down(cardIndex))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().withColor(cardIndex.getIndexedInstance().getColor())).get();
                    trash(target);
                }
            }
        }
    }
}
