package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_AltoDissonaExplosiveGun extends Card {

    public SIGNI_W2_AltoDissonaExplosiveGun()
    {
        setImageSets("WXDi-P16-063");

        setOriginalName("爆砲　アルト//ディソナ");
        setAltNames("バクホウアルトディソナ Bakuhou Auro Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、そのアタックがこのターン一度目の場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。そのアタックがこのターン二度目の場合、ターン終了時まで、このシグニは@>@C：対戦相手のシグニがこのシグニとのバトルによってバニッシュされる場合、エナゾーンに置かれる代わりにトラッシュに置かれる。@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Alto//Disonna, Erupting Cannon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if that is the first attack this turn, it gets +4000 power until the end of your opponent's next end phase. If that is the second attack this turn, it gains@>@C: If a SIGNI on your opponent's field is vanished through battle with this SIGNI, it is put into the trash instead of the Ener Zone.@@until end of turn." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Alto//Dissona, Explosive Gun");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if it is the first attack this turn, until the end of your opponent's next turn, this SIGNI gets +4000 power. If it is the second attack this turn, until end of turn, this SIGNI gains:" +
                "@>@C: If this SIGNI would banish your opponent's SIGNI in battle, it is put into the trash instead of the ener zone.@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "爆炮 阿尔特//失调");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，那次攻击是这个回合第一次的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。那次攻击是这个回合第二次的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@C :对战对手的精灵因为与这只精灵的战斗被破坏的场合，放置到能量区，作为替代，放置到废弃区。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            int count = GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && isOwnCard(event.getCaller()));
            
            if(count == 1)
            {
                gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            } else if(count == 2)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.SOURCE, OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler)
                ));
                
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() == null && !isOwnCard(event.getCallerCardIndex());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionTrash(list.getSourceEvent().getCallerCardIndex()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
