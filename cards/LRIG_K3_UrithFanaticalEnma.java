package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.events.EventPowerChanged;

public final class LRIG_K3_UrithFanaticalEnma extends Card {

    public LRIG_K3_UrithFanaticalEnma()
    {
        setImageSets("WXDi-P14-009", "WXDi-P14-009U");
        setLinkedImageSets("WXDi-P14-TK01","WXDi-P14-TK02","WXDi-P14-TK03","WXDi-P14-TK04","WXDi-P14-TK05");

        setOriginalName("熱狂の閻魔　ウリス");
        setAltNames("ネッキョウノエンマウリス Nekkyou no Enma Urisu");
        setDescription("jp",
                "@U $T1：あなたのターンの間、対戦相手のシグニ１体のパワーが０以下になったとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。\n" +
                "@E：各プレイヤーは自分のデッキの上からカードを５枚トラッシュに置く。\n" +
                "@A @[エクシード４]@：フェゾーネマジックのクラフトから２種類を１枚ずつ公開しルリグデッキに加える。"
        );

        setName("en", "Urith, Fanatical Enma");
        setDescription("en",
                "@U $T1: During your turn, when the power of a SIGNI on your opponent's field becomes 0 or less, target SIGNI on your opponent's field gets --5000 power until end of turn.\n@E: Each player puts the top five cards of their deck into their trash.\n@A @[Exceed 4]@: Reveal two different Fesonne Magic Craft and add them to your LRIG Deck. "
        );
        
        setName("en_fan", "Urith, Fanatical Enma");
        setDescription("en_fan",
                "@U $T1: During your turn, when the power of your opponent's SIGNI becomes 0 or less, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power.\n" +
                "@E: Each player puts the top 5 cards of their deck into the trash.\n" +
                "@A @[Exceed 4]@: Reveal 2 different Fessone Magic crafts one by one, and add them to your LRIG deck."
        );

		setName("zh_simplified", "热狂的阎魔 乌莉丝");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当对战对手的精灵1只的力量在0以下时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n" +
                "@E :各玩家从自己的牌组上面把5张牌放置到废弃区。\n" +
                "@A @[超越 4]@:从音乐节魔术的衍生把2种类各1张公开加入分身牌组。（音乐节魔术有5种类）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.POWER_CHANGED, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && !isOwnCard(caller) && EventPowerChanged.getDataNewValue() <= 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            millDeck(5);
            millDeck(getOpponent(), 5);
        }

        private void onActionEff()
        {
            playerChoiceFessoneMagic();
        }
    }
}
