package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_SerinaSumi extends Card {

    public SIGNI_W1_SerinaSumi()
    {
        setImageSets("WXDi-CP02-065");

        setOriginalName("鷲見セリナ");
        setAltNames("スミセリナ Sumi Serina");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、それを手札に加える。" +
                "~{{C：対戦相手のターンの間、このシグニは[[シャドウ（レベル２以下）]]を得る。@@" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Sumi Serina");
        setDescription("en",
                "@U: At the end of your turn, you may put two <<Blue Archive>> cards from your Ener Zone into your trash. If you do, add target SIGNI with a #G from your trash to your hand.~{{C: During your opponent's turn, this SIGNI gains [[Shadow -- Level two or less]].@@" +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Serina Sumi");
        setDescription("en_fan",
                "@U: At the end of your turn, target 1 #G @[Guard]@ SIGNI from your trash, and you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, add it to your hand." +
                "~{{C: During your opponent's turn, this SIGNI gains [[Shadow (level 2 or lower)]].@@" +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "鹫见芹娜");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，从你的废弃区把持有#G的精灵1张作为对象，可以从你的能量区把<<ブルアカ>>牌2张放置到废弃区。这样做的场合，将其加入手牌。\n" +
                "~{{C:对战对手的回合期间，这只精灵得到[[暗影（等级2以下）]]。@@" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            ConstantAbility cont = registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                
                if(trash(data) == 2)
                {
                    addToHand(target);
                }
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return (CardType.isSIGNI(cardIndexSource.getCardReference().getType()) || CardType.isLRIG(cardIndexSource.getCardReference().getType())) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            draw(1);

            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
