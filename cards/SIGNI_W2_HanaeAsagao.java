package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_HanaeAsagao extends Card {

    public SIGNI_W2_HanaeAsagao()
    {
        setImageSets("WX25-CP1-058");

        setOriginalName("朝顔ハナエ");
        setAltNames("アサガオハナエ Asagao Hanae");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、それを手札に加える。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Asagao Hanae");

        setName("en_fan", "Hanae Asagao");
        setDescription("en",
                "@U: At the end of your turn, target 1 #G @[Guard]@ SIGNI from your trash, and you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, add it to your hand." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C@#: Can't attack."
        );

		setName("zh_simplified", "朝颜花绘");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，从你的废弃区把持有#G的精灵1张作为对象，可以从你的能量区把<<ブルアカ>>牌2张放置到废弃区。这样做的场合，将其加入手牌。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

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

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().guard().fromTrash()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                
                if(trash(data) > 0)
                {
                    addToHand(target);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
