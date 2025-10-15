package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.FieldConst;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXFieldBackground;

public final class LRIG_G3_MelCheers extends Card {

    public LRIG_G3_MelCheers()
    {
        setImageSets("WXDi-P09-007", "WXDi-P09-007U");
        setLinkedImageSets("WXDi-P09-TK01A","WXDi-P09-TK02A","WXDi-P09-TK03A");

        setOriginalName("メル＝チアーズ");
        setAltNames("メルチアーズ Meru Chiaazu");
        setDescription("jp",
                "@E：あなたのルリグデッキに《コードイート　ケチャチャ》１枚と《コードイート　セアブラマシマシ》１枚と《コードイート　オンタマ》１枚を加える。\n" +
                "@A %X：あなたのシグニ１体を対象とし、あなたのルリグデッキからクラフトであるシグニ１枚をそれの【アクセ】にする。\n" +
                "@A $G1 %G0：次の対戦相手のターンの間、対戦相手はスペルと@A能力を使用できない。"
        );

        setName("en", "Mel - Cheers");
        setDescription("en",
                "@E: Add a \"Ketchacha, Code: Eat\", a \"Back Fat Mashimashi, Code: Eat\", and a \"Soft-Boiled Egg, Code: Eat\" to your LRIG Deck.\n" +
                "@A %X: Attach a Craft SIGNI from your LRIG Deck to target SIGNI on your field as an [[Acce]].\n" +
                "@A $G1 %G0: During your opponent's next turn, your opponent cannot use spells or @A abilities."
        );
        
        setName("en_fan", "Mel-Cheers");
        setDescription("en_fan",
                "@E: Add 1 \"Code Eat Ketchacha\", 1 \"Code Eat Seaburamashimashi\", and 1 \"Code Eat Soft-Boiled Egg\" crafts into your LRIG deck.\n" +
                "@A %X: Target 1 of your SIGNI, and attach 1 craft SIGNI from your LRIG deck to it as an [[Accessory]].\n" +
                "@A $G1 %G0: During your opponent's next turn, your opponent can't use spells or @A abilities."
        );

		setName("zh_simplified", "梅露=干杯");
        setDescription("zh_simplified", 
                "@E :你的分身牌组加入《コードイート　ケチャチャ》1张和《コードイート　セアブラマシマシ》1张和《コードイート　オンタマ》1张。\n" +
                "@A %X:你的精灵1只作为对象，从你的分身牌组把衍生的精灵1张作为其的[[附属]]。\n" +
                "@A $G1 %G0下一个对战对手的回合期间，对战对手不能把魔法和@A能力使用。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            returnToDeck(craft(getLinkedImageSets().get(0)), DeckPosition.TOP);
            returnToDeck(craft(getLinkedImageSets().get(1)), DeckPosition.TOP);
            returnToDeck(craft(getLinkedImageSets().get(2)), DeckPosition.TOP);
        }

        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().SIGNI().attachable(GameConst.CardUnderType.ATTACHED_ACCESSORY).getValidTargetsCount() == 0 ||
                    new TargetFilter().own().SIGNI().withState(GameConst.CardStateFlag.IS_CRAFT).fromLocation(CardLocation.DECK_LRIG).getValidTargetsCount() == 0 ?
                    ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI().attachable(GameConst.CardUnderType.ATTACHED_ACCESSORY)).get();

            if(target != null)
            {
                CardIndex cardIndex = searchDeck(new TargetFilter(TargetHint.ATTACH).own().SIGNI().withState(GameConst.CardStateFlag.IS_CRAFT), Deck.DeckType.LRIG).get();

                if(cardIndex != null)
                {
                    attach(target, cardIndex, GameConst.CardUnderType.ATTACHED_ACCESSORY);
                }
            }
        }

        private void onActionEff2()
        {
            ConstantAbility attachedConst = new ConstantAbility(
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_SPELL, TargetFilter.HINT_OWNER_OP, data -> RuleCheckState.BLOCK),
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_ABILITY, TargetFilter.HINT_OWNER_OP, data -> data.getSourceAbility() instanceof ActionAbility ? RuleCheckState.BLOCK : RuleCheckState.IGNORE)
            );
            attachedConst.setCondition(() -> !isOwnTurn() ? ConditionState.OK : ConditionState.BAD);
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
            GFX.attachToChronoRecord(record, new GFXFieldBackground(getOpponent(), "thorns", 2730,1024, FieldConst.FIELD_CARD_WIDTH*2 + FieldConst.FIELD_ZONE_HSPACING+10,FieldConst.FIELD_ZONE_VSPACING+100).withCenterOffset());
            attachPlayerAbility(getOwner(), attachedConst, record);
        }
    }
}

