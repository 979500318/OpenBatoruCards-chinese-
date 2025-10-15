package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventRefresh;

public final class LRIG_K3_AlfouDivaOfApplause extends Card {

    public LRIG_K3_AlfouDivaOfApplause()
    {
        setImageSets("WXDi-P11-008", "WXDi-P11-008U");

        setOriginalName("喝采の歌姫　アルフォウ");
        setAltNames("カッサイノウタヒメアルフォウ Kassai no Utahime Arufou");
        setDescription("jp",
                "@U $T1：対戦相手がリフレッシュしたとき、カードを１枚引くか【エナチャージ１】をする。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %K0：対戦相手のデッキの上からカードを８枚トラッシュに置く。その後、対戦相手のトラッシュから#Gを持たないカードを３枚まで対象とし、それらをゲームから除外する。"
        );

        setName("en", "Alfou, Cheering Diva");
        setDescription("en",
                "@U $T1: When your opponent refreshes their deck, draw a card or [[Ener Charge 1]].\n" +
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@A $G1 %K0: Put the top eight cards of your opponent's deck into their trash. Then, remove up to three target cards without a #G in your opponent's trash from the game."
        );
        
        setName("en_fan", "Alfou, Diva of Applause");
        setDescription("en_fan",
                "@U $T1: When your opponent refreshes, draw 1 card or [[Ener Charge 1]].\n" +
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $G1 %K0: Put the top 8 cards of your opponent's deck into the trash. Then, target up to 3 cards without #G @[Guard]@ from your opponent's trash, and exclude them from the game."
        );

		setName("zh_simplified", "喝彩的歌姬 阿尔芙");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手重构时，抽1张牌或[[能量填充1]]。\n" +
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %K0从对战对手的牌组上面把8张牌放置到废弃区。然后，从对战对手的废弃区把不持有#G的牌3张最多作为对象，将这些从游戏除外。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.REFRESH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return ((EventRefresh)getEvent()).getPlayer() == getOpponent() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }

        private void onActionEff()
        {
            millDeck(getOpponent(), 8);

            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.EXCLUDE).OP().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            exclude(data);
        }
    }
}
