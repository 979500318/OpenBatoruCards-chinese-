package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCrush;

public final class LRIG_W3_SaoriJoumaeEtOmniaVeritas extends Card {

    public LRIG_W3_SaoriJoumaeEtOmniaVeritas()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-012", "WX25-CP1-012U");

        setOriginalName("錠前サオリ[et omnia vanitas！]");
        setAltNames("ジョウマエサオリエトオムニアヴァニタス Joumae Saori Eto Omunia Vanitasu");
        setDescription("jp",
                "@U：あなたのルリグかシグニがアタックによって対戦相手のライフクロスを１枚以上クラッシュしたとき、そのアタック終了時、対戦相手が%Xを支払わないかぎり、対戦相手にダメージを与える。\n" +
                "@A $G1 %W0：対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。" +
                "~{{A $G1 %W0：あなたのデッキの上からカードを５枚見る。その中からシグニを２枚まで場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Joumae Saori [et omnia veritas]");

        setName("en_fan", "Saori Joumae [et omnia veritas]");
        setDescription("en",
                "@U: Whenever your LRIG or SIGNI crushes 1 or more of your opponent's life cloth by an attack, at the end of that attack, damage your opponent unless they pay %X.\n" +
                "@A $G1 %W0: Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand." +
                "~{{A $G1 %W0: Look at the top 5 cards of your deck. Put up to 2 SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "锭前纱织[et omnia vanitas！]");
        setDescription("zh_simplified", 
                "@U 当你的分身或精灵因为攻击把对战对手的生命护甲1张以上击溃时，那次攻击结束时，如果对战对手不把%X:支付，那么给予对战对手伤害。\n" +
                "@A $G1 %W0:对战对手的等级2以下的精灵1只作为对象，将其返回手牌。\n" +
                "~{{A$G1 %W0:从你的牌组上面看5张牌。从中把精灵2张最多出场，剩下的任意顺序放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SAORI);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && isOwnCard(getEvent().getSourceCardIndex()) &&
                    getEvent().getSourceAbility() == null && getEvent().isAtOnce(1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            callDelayedEffect(((EventCrush)getEvent()).requestPostAttackTrigger(), () -> {
                if(!payEner(getOpponent(), Cost.colorless(1)))
                {
                    damage(getOpponent());
                }
            });
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable());
            putOnField(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
