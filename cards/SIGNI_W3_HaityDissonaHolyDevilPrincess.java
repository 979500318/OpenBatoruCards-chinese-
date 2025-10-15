package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_HaityDissonaHolyDevilPrincess extends Card {

    public SIGNI_W3_HaityDissonaHolyDevilPrincess()
    {
        setImageSets("WXDi-P13-044", "WXDi-P13-044P");

        setOriginalName("聖魔姫　ハイティ//ディソナ");
        setAltNames("セイマキハイティディソナ Seimaki Haiti Disona");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、このシグニを場からトラッシュに置き%W %X %Xを支払ってもよい。そうした場合、それをトラッシュに置く。\n" +
                "@A #D：次の対戦相手のターン終了時まで、このシグニは@>@C：対戦相手のターンの間、【シャドウ】を得る。@@を得る。" +
                "~#：カードを２枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Highty//Dissona, Blessed Evil Queen");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, you may put this SIGNI on your field into its owner's trash and pay %W %X %X. If you do, put target SIGNI on your opponent's field into its owner's trash.\n@A #D: This SIGNI gains@>@C: During your opponent's turn, this SIGNI gains [[Shadow]].@@until the end of your opponent's next end phase." +
                "~#Draw two cards. The SIGNI in your hand gain a #G this turn."
        );
        
        setName("en_fan", "Haity//Dissona, Holy Devil Princess");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, you may put this SIGNI into the trash and pay %W %X %X. If you do, target 1 of your opponent's SIGNI, and put it into the trash.\n" +
                "@A #D: Until the end of your opponent's next turn, this SIGNI gains:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow]].@@" +
                "~#Draw 2 cards. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "圣魔姬 海蒂//失调");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，对战对手的精灵1只作为对象，可以把这只精灵从场上放置到废弃区并支付%W%X %X。这样做的场合，将其放置到废弃区。\n" +
                "@A #D:直到下一个对战对手的回合结束时为止，这只精灵得到\n" +
                "@>@C :对战对手的回合期间，得到[[暗影]]。@@" +
                "~#抽2张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && payAll(new TrashCost(), new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(2))))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                trash(target);
            }
        }
        
        private void onActionEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private void onLifeBurstEff()
        {
            draw(2);
            
            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEff2ModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
