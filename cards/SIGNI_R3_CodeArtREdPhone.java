package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R3_CodeArtREdPhone extends Card {

    public SIGNI_R3_CodeArtREdPhone()
    {
        setImageSets("WX25-P2-078");

        setOriginalName("コードアート　Aカデンワ");
        setAltNames("コードアートエーカデンワ Koodo Aato Ee Kadenwa Akadenwa Red Phone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。このシグニが覚醒状態の場合、代わりに対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを３枚まで対象とし、それらをトラッシュに置く。"
        );

        setName("en", "Code Art R Ed Phone");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash. If this SIGNI is awakened, instead target up to 3 cards from your opponent's ener zone that don't share a common color with your opponent's center LRIG, and put them into the trash."
        );

		setName("zh_simplified", "必杀代号 红色电话机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。这只精灵在觉醒状态的场合，作为替代，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌3张最多作为对象，将这些放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);

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
            TargetFilter filter = new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner();
            DataTable<CardIndex> data;
            if(!isState(CardStateFlag.AWAKENED))
            {
                data = playerTargetCard(filter);
            } else {
                data = playerTargetCard(0,3, filter);
            }
            trash(data);
        }
    }
}
