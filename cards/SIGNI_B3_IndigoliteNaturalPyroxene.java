package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_IndigoliteNaturalPyroxene extends Card {

    public SIGNI_B3_IndigoliteNaturalPyroxene()
    {
        setImageSets("WXDi-P09-041");

        setOriginalName("羅輝石　インディゴライト");
        setAltNames("ラキセキインディゴライト Rakiseki Indigoraito");
        setDescription("jp",
                "@C：あなたのトラッシュに＜宝石＞のシグニが５枚以上あるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U $T1：あなたの＜宝石＞のシグニ１体がバニッシュされたとき、対戦相手は手札を１捨てる。\n" +
                "@A $T1 @[手札を１捨てる]@：あなたのデッキの上からカードを２枚見る。その中からカード１枚を手札に加え、残りをトラッシュに置く。" +
                "~#：カードを２枚引く。あなたの手札から＜宝石＞のシグニを１枚まで場に出す。"
        );

        setName("en", "Indigolite, Natural Pyroxene");
        setDescription("en",
                "@C: As long as there are five or more <<Jewel>> SIGNI in your trash, this SIGNI gets +3000 power.\n" +
                "@U $T1: When a <<Jewel>> SIGNI on your field is vanished, your opponent discards a card.\n" +
                "@A $T1 @[Discard a card]@: Look at the top two cards of your deck. Add a card from among them to your hand and put the rest into your trash." +
                "~#Draw two cards. Put up to one <<Jewel>> SIGNI from your hand onto your field. "
        );
        
        setName("en_fan", "Indigolite, Natural Pyroxene");
        setDescription("en_fan",
                "@C: As long as there are 5 or more <<Gem>> SIGNI in your trash, this SIGNI gets +3000 power.\n" +
                "@U $T1: When your <<Gem>> SIGNI is banished, your opponent discards 1 card from their hand.\n" +
                "@A $T1 @[Discard 1 card from your hand]@: Look at the top 2 cards of your deck. Add 1 card from among them to your hand, and put the rest into the trash." +
                "~#Draw 2 cards. Put up to 1 <<Gem>> SIGNI from your hand onto the field."
        );

		setName("zh_simplified", "罗辉石 蓝碧玺");
        setDescription("zh_simplified", 
                "@C :你的废弃区的<<宝石>>精灵在5张以上时，这只精灵的力量+3000。\n" +
                "@U $T1 :当你的<<宝石>>精灵1只被破坏时，对战对手把手牌1张舍弃。\n" +
                "@A $T1 手牌1张舍弃:从你的牌组上面看2张牌。从中把1张牌加入手牌，剩下的放置到废弃区。" +
                "~#抽2张牌。从你的手牌把<<宝石>>精灵1张最多出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new DiscardCost(1), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.GEM).fromTrash().getValidTargetsCount() >= 5 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.GEM) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }

        private void onActionEff()
        {
            look(2);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(target);
            
            trash(getCardsInLooked(getOwner()));
        }

        private void onLifeBurstEff()
        {
            draw(2);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.GEM).fromHand().playable()).get();
            putOnField(cardIndex);
        }
    }
}
