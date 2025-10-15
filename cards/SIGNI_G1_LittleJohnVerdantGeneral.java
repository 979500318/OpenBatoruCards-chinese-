package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionCallDelayedEffect;
import open.batoru.core.gameplay.actions.ActionFlip;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G1_LittleJohnVerdantGeneral extends Card {
    
    public SIGNI_G1_LittleJohnVerdantGeneral()
    {
        setImageSets("WXDi-P05-069");
        setLinkedImageSets("WXDi-P01-040");
        
        setOriginalName("翠将　リトルジョン");
        setAltNames("スイショウリトルジョン Suishou Ritoru Jon");
        setDescription("jp",
                "@C：あなたの場かエナゾーンに《翠将姫　ロビンフッド》があるかぎり、このシグニのパワーは＋5000される。\n" +
                "@C：あなたの《翠将姫　ロビンフッド》は@>@C：このシグニがアタックする場合、代わりにあなたのシグニを２体まで裏向きにしてアタックし、このターン終了時、これによって裏向きにしたシグニを、同じ場所にシグニがない場合、表向きにする。@@を得る。"
        );
        
        setName("en", "Little John, Jade General");
        setDescription("en",
                "@C: As long as there is \"Robin Hood, Jade General Queen\" on your field or Ener Zone, this SIGNI gets +5000 power.\n" +
                "@C: \"Robin Hood, Jade General Queen\" on your field gains@>@C: If this SIGNI attacks, instead turn up to two SIGNI on your field face down and attack. At the end of this turn, if a SIGNI is not in the same position as a SIGNI turned face down this way, turn that SIGNI face up."
        );
        
        setName("en_fan", "Little John, Verdant General");
        setDescription("en_fan",
                "@C: As long as there is a \"Robin Hood, Verdant General Princess\" on your field or in your ener zone, this SIGNI gets +5000 power.\n" +
                "@C: Your \"Robin Hood, Verdant General Princess\" gain:" +
                "@>@C: If this SIGNI would attack, instead turn up to 2 of your SIGNI face down and then attack. At the end of this turn, turn the SIGNI turned face down this way face up if there is no SIGNI in the same zone."
        );
        
		setName("zh_simplified", "翠将 小约翰");
        setDescription("zh_simplified", 
                "@C :你的场上或能量区有《翠将姫　ロビンフッド》时，这只精灵的力量+5000。\n" +
                "@C :你的《翠将姫　ロビンフッド》得到\n" +
                "@>@C :这只精灵攻击的场合，作为替代，你的精灵2只最多变为里向并攻击，这个回合结束时，因此变为里向的精灵在，相同场所没有精灵的场合，变为表向。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(5000));
            registerConstantAbility(new TargetFilter().own().SIGNI().withName("翠将姫　ロビンフッド"), new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEff1Cond()
        {
            TargetFilter filter = new TargetFilter().own().SIGNI().withName("翠将姫　ロビンフッド");
            return filter.getValidTargetsCount() > 0 || filter.fromEner().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.ATTACK, OverrideScope.CALLER,OverrideFlag.DONT_BLOCK, this::onAttachedConstEffModOverrideHandler))
            );
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addTargetAction(0,2, new TargetFilter(TargetHint.FLIP).own().SIGNI()).setOnActionCompleted(() -> {
                if(list.getAction(0).isSuccessful())
                {
                    ((ActionFlip)list.getAction(1)).setCardIndex((CardIndex)list.getAction(0).getDataTable().get(0));

                    if(list.getAction(0).getDataTable().size() < 2) list.getAction(1).setAtOnce(null, 0,1);
                    else ((ActionFlip)list.getAction(2)).setCardIndex((CardIndex)list.getAction(0).getDataTable().get(1));
                }
            });

            ActionFlip flip = new ActionFlip(CardFace.BACK);
            flip.setAtOnce(null, 0,2);
            list.addAction(flip);
            flip = new ActionFlip(CardFace.BACK);
            flip.setAtOnce((GameAction<CardIndex>)list.getAction(2), 1,2);
            list.addNonMandatoryAction(flip);

            list.addAction(new ActionCallDelayedEffect(list.getSourceEvent().getCallerCardIndex(), sourceAbilityRC, new ChronoRecord(ChronoDuration.turnEnd()), () -> {
                flip((DataTable<CardIndex>)list.getAction(0).getDataTable(), CardFace.FRONT);
            }));
        }
    }
}
