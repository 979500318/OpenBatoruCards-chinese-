package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.ActionBanish;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_K3_Code2434LainPaterson extends Card {

    public SIGNI_K3_Code2434LainPaterson()
    {
        setImageSets("WXDi-CP01-032");

        setOriginalName("コード２４３４　レイン・パターソン");
        setAltNames("コードニジサンジレインパターソン Koodo Nijisanji Rein Pataason");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの他のシグニ１体がバニッシュされる場合、代わりにこのシグニをバニッシュしてもよい。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜バーチャル＞の場合、対戦相手のデッキの上からカードを３枚トラッシュに置く。この方法でトラッシュに置かれたシグニのレベルの合計が７の場合、対戦相手のデッキの上からカードを７枚トラッシュに置く。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Lain Paterson, Code 2434");
        setDescription("en",
                "@C: During your opponent's turn, if another SIGNI on your field would be vanished, you may instead vanish this SIGNI.\n@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Virtual>>, put the top three cards of your opponent's deck into their trash. If the total level of the SIGNI put into the trash this way is exactly seven, put the top seven cards of your opponent's deck into their trash." +
                "~#Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code 2434 Lain Paterson");
        setDescription("en_fan",
                "@C: During your opponent's turn, if 1 of your other SIGNI would be banished, you may banish this SIGNI instead.\n" +
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Virtual>> SIGNI, put the top 3 cards of your opponent's deck into the trash. If the total level of all the SIGNI put into the trash this way is 7, put the top 7 cards of your opponent's deck into the trash." +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "2434代号 兰·帕特森");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的其他的精灵1只被破坏的场合，作为替代，可以把这只精灵破坏。\n" +
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<バーチャル>>的场合，从对战对手的牌组上面把3张牌放置到废弃区。这个方法放置到废弃区的精灵的等级的合计在7的场合，从对战对手的牌组上面把7张牌放置到废弃区。" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().except(cardId), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideHandler)
            ));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionBanish(getCardIndex()));
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.VIRTUAL)).getValidTargetsCount() == 0)
            {
                DataTable<CardIndex> data = millDeck(getOpponent(), 3);
                
                if(data.get() != null)
                {
                    int level = 0;
                    for(int i=0;i<data.size();i++) level += data.get(i).getIndexedInstance().getLevelByRef();
                    
                    if(level == 7)
                    {
                        millDeck(getOpponent(), 7);
                    }
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
